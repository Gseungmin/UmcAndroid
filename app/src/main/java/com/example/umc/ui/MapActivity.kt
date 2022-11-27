package com.example.umc.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.umc.Constants
import com.example.umc.Constants.PLACE_API_KEY
import com.example.umc.R
import com.example.umc.databinding.ActivityMapBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import kotlin.math.ln


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding

    //장소 유형 리스트
    val dialogData = arrayOf(
        "accounting", "airport", "amusement_park",
        "aquarium", "art_gallery", "atm", "bakery",
        "bank", "bar", "beauty_salon", "bicycle_store",
        "book_store", "bowling_alley", "bus_station",
        "cafe", "campground", "car_dealer", "car_rental",
        "car_repair", "car_wash", "casino", "cemetery",
        "church", "city_hall", "clothing_store", "convenience_store",
        "courthouse", "dentist", "department_store", "doctor",
        "drugstore", "electrician", "electronics_store", "embassy",
        "fire_station", "florist", "funeral_home", "furniture_store",
        "gas_station", "gym", "hair_care", "hardware_store", "hindu_temple",
        "home_goods_store", "hospital", "insurance_agency",
        "jewelry_store", "laundry", "lawyer", "library", "light_rail_station",
        "liquor_store", "local_government_office", "locksmith", "lodging",
        "meal_delivery", "meal_takeaway", "mosque", "movie_rental", "movie_theater",
        "moving_company", "museum", "night_club", "painter", "park", "parking",
        "pet_store", "pharmacy", "physiotherapist", "plumber", "police", "post_office",
        "primary_school", "real_estate_agency", "restaurant", "roofing_contractor",
        "rv_park", "school", "secondary_school", "shoe_store", "shopping_mall",
        "spa", "stadium", "storage", "store", "subway_station", "supermarket",
        "synagogue", "taxi_stand", "tourist_attraction", "train_station",
        "transit_station", "travel_agency", "university", "eterinary_care","zoo"
    )

    //현재 위치
    var myLocation : Location? = null

    //마커 정보를 위한 리스트들
    var nearby_lat = ArrayList<Double>()
    var nearby_log = ArrayList<Double>()
    var nearby_name = ArrayList<String>()
    var nearby_vicinity = ArrayList<String>()
    var nearby_marker = ArrayList<Marker>()

    //허용받은 권한 목록
    val permission_list = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    //gps 정보를 관리할 매니저
    private lateinit var manager : LocationManager

    //위치정보 측정 성공시 호출될 메소드가 구현되어 있는 리스너
    //위치측정을 한번 하게되면 다시 측정하지 않게 연결을 끊을 것이므로 전역 변수로 관리
    private lateinit var locationListener: LocationListener

    //구글맵을 제어해야 하므로 구글맵 객체도 받아옴
    private lateinit var googleMap: GoogleMap

    private lateinit var geocoder: Geocoder

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Place 초기화
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, PLACE_API_KEY)
        }
        val placesClient = Places.createClient(this)

        //권한 요청
        requestPermissions(permission_list, 0)

        //맵의 상태가 변경되면 호출될 메소드가 구현되어 있는 곳 등록
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //AutocompleteSupportFragment 초기화
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        // 리턴받은 정보 구체화해서 셋팅
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG))

        // 장소 선택시 발생하는 리스너
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.d("PLACE", "Place: ${place.name}, ${place.address}, ${place.latLng}")

                val lat = place.latLng.latitude
                val lng = place.latLng.longitude


                val loc1 = LatLng(lat, lng)
                //지도를 이동시키기 위한 객체 생성
                val loc2 = CameraUpdateFactory.newLatLngZoom(loc1, 15f)

                // 이동
                googleMap.animateCamera(loc2)
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.d("PLACE", "An error occurred: $status")
            }
        })

        binding.dialog.setOnClickListener {
            val placeListBuilder = AlertDialog.Builder(this)
            placeListBuilder.setTitle("장소 종류 선택")
            placeListBuilder.setNegativeButton("취소", null)
            placeListBuilder.setNeutralButton("초기화") { dialogInterface, i ->
                //마커 제거
                for (m in nearby_marker) {
                    m.remove()
                }

                //리스트 초기화
                nearby_lat.clear()
                nearby_name.clear()
                nearby_vicinity.clear()
                nearby_log.clear()
                nearby_marker.clear()
            }
            placeListBuilder.setItems(dialogData) {
                    dialogInterface, i ->

                //마커 제거
                for (m in nearby_marker) {
                    m.remove()
                }

                //리스트 초기화
                nearby_lat.clear()
                nearby_name.clear()
                nearby_vicinity.clear()
                nearby_log.clear()
                nearby_marker.clear()

                getNearbyPlaceData(dialogData[i])
            }

            placeListBuilder.show()
        }
    }

    // 위치 값을 받아 지도를 이동시키는 메소드
    private fun setUserLocation(location: Location) {

        //위치 측정을 중단
        manager.removeUpdates(locationListener)

        //위도와 경도 값을 관리하는 객체
        val loc1 = LatLng(location.latitude, location.longitude)
        //지도를 이동시키기 위한 객체 생성
        val loc2 = CameraUpdateFactory.newLatLngZoom(loc1, 15f)

        myLocation = location
        Log.d("NEARBY", myLocation.toString())

        Log.d("MAP2", location.toString())

        // 이동
        googleMap.animateCamera(loc2)
    }

    //현재 위치를 측정하는 메소드
    private fun getMyLocation() {
        // 위치 정보를 관리하는 매니저 추출
        manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // 저장되어 있는 위치값이 있으면 가져옴
        // 먼저 권한 체크부터 실행
        val auth1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val auth2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        //허용을 하지 않은 경우 함수 종료
        if ((auth1 != PackageManager.PERMISSION_GRANTED) && (auth2 != PackageManager.PERMISSION_GRANTED)) {
            return
        }

        //권한이 허용된 경우
        //GPS를 이용해 데이터 확인
        val location1 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        //네트워크 망을 통해 데이터 확인
        val location2 = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        //새로운 위치 측정 요청
        locationListener = LocationListener {
            setUserLocation(it)
        }

        //이미 저장된 위치 셋팅
        if (location1 != null) {
            setUserLocation(location1)
        } else if (location2 != null) {
            setUserLocation(location2)
        }

        //GPS를 이용할 수 있는 환경이라면, NETWORK를 이용할 수 있는 환경이라면
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
            manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true) {
            manager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
        }
    }

    //현재 위치 조회 메소드
    //지도가 준비 완료되면 호출되는 메소드, p0가 구글 지도 객체
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0

        //구글 지도에 관련된 옵션을 설정한 후 현재 위치를 측정하도록 설정
        //구글 지도의 옵션 설정을 위해 권한 확인
        val auth1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val auth2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        //권한을 허용하는 경우 작업이 이루어짐
        if ((auth1 == PackageManager.PERMISSION_GRANTED) && (auth2 == PackageManager.PERMISSION_GRANTED)) {
            Log.d("MAP", "권한이 허용됨")
            //확대 축소버튼이 나타나게 됨
            googleMap.uiSettings.isZoomControlsEnabled = true

            //현재 위치를 표시하는 기능 제공
            googleMap.isMyLocationEnabled = true
        }

        //현재 위치를 측정
        getMyLocation()
    }

    /**
     * 주위 정보를 가져오는 메소드 추가
     */
    private fun getNearbyPlaceData(type : String) {

        thread {
            var site = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                    "?location=${myLocation?.latitude},${myLocation?.longitude}" +
                    "&radius=1000" +
                    "&type=${type}" +
                    "&key=${Constants.PLACE_API_KEY}" +
                    "&language=ko"

            Log.d("NEARBY",site)

            val url = URL(site)
            val conn = url.openConnection() as HttpURLConnection

            //데이터를 읽어옴
            val isr = InputStreamReader(conn.inputStream, "UTF-8")
            val br = BufferedReader(isr)

            var str:String? = null
            val buf = StringBuffer()

            do {
                str = br.readLine()
                if (str != null) {
                    buf.append(str)
                }
            } while (str != null)

            val data = buf.toString()

            Log.d("NEARBY",data)

            //JSON 객체 생성
            val root = JSONObject(data)

            if (root.getString("status") == "OK") {
                val results = root.getJSONArray("results")

                for (i in 0 until results.length()){
                    val results_item = results.getJSONObject(i)

                    val geo = results_item.getJSONObject("geometry")
                    val location = geo.getJSONObject("location")
                    val lat = location.getDouble("lat")
                    val lng = location.getDouble("lng")
                    val name = results_item.getString("name")
                    val vicinity = results_item.getString("vicinity")

                    Log.d("NEARBY",location.toString())
                    Log.d("NEARBY",name)
                    Log.d("NEARBY",vicinity)
                    Log.d("NEARBY","==============")

                    nearby_lat.add(lat)
                    nearby_log.add(lng)
                    nearby_name.add(name)
                    nearby_vicinity.add(vicinity)

                    runOnUiThread{
                        for (k in 0 until nearby_lat.size){
                            val loc = LatLng(nearby_lat[i], nearby_log[i])
                            var placeMarkerOptions = MarkerOptions()
                            placeMarkerOptions.position(loc)
                            placeMarkerOptions.title(nearby_name[i])
                            placeMarkerOptions.snippet(nearby_vicinity[i])

                            val placeMarker = googleMap.addMarker(placeMarkerOptions)
                            nearby_marker.add(placeMarker!!)
                        }
                    }
                }
            }
        }
    }
}