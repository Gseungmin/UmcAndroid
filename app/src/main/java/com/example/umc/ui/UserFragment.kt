package com.example.umc.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.umc.R
import com.example.umc.databinding.FragmentOrderBinding
import com.example.umc.databinding.FragmentUserBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng

class UserFragment : Fragment(), OnMapReadyCallback {

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

    //허용받은 권한 목록
    val permission_list = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var binding: FragmentUserBinding

    //gps 정보를 관리할 매니저
    private lateinit var manager : LocationManager

    //위치정보 측정 성공시 호출될 메소드가 구현되어 있는 리스너
    //위치측정을 한번 하게되면 다시 측정하지 않게 연결을 끊을 것이므로 전역 변수로 관리
    private lateinit var locationListener: LocationListener

    //구글맵을 제어해야 하므로 구글맵 객체도 받아옴
    private lateinit var googleMap: GoogleMap

    private lateinit var mapView : MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(layoutInflater)
        val view = binding.root

        //권한 요청
        requestPermissions(permission_list, 0)

        //맵의 상태가 변경되면 호출될 메소드가 구현되어 있는 곳 등록
        mapView = binding.mapView as MapView
        mapView.onCreate(savedInstanceState)

        Log.d("MAP1", mapView.toString())

        mapView.getMapAsync(this)

        binding.dialog.setOnClickListener {
            val placeListBuilder = AlertDialog.Builder(requireContext())
            placeListBuilder.setTitle("장소 종류 선택")
            placeListBuilder.setNegativeButton("취소", null)
            placeListBuilder.setNeutralButton("초기화") { dialogInterface, i ->
//                //마커 제거
//                for (m in nearby_marker) {
//                    m.remove()
//                }
//
//                //리스트 초기화
//                nearby_lat.clear()
//                nearby_name.clear()
//                nearby_vicinity.clear()
//                nearby_log.clear()
//                nearby_marker.clear()
            }
            placeListBuilder.setItems(dialogData) {
                    dialogInterface, i ->
//
//                //마커 제거
//                for (m in nearby_marker) {
//                    m.remove()
//                }
//
//                //리스트 초기화
//                nearby_lat.clear()
//                nearby_name.clear()
//                nearby_vicinity.clear()
//                nearby_log.clear()
//                nearby_marker.clear()
//
//                getNearbyPlaceData(dialogData[i])
            }

            placeListBuilder.show()
        }

        return view
    }

    // 위치 값을 받아 지도를 이동시키는 메소드
    private fun setUserLocation(location: Location) {

        //위치 측정을 중단
        manager.removeUpdates(locationListener)

        //위도와 경도 값을 관리하는 객체
        val loc1 = LatLng(location.latitude, location.longitude)
        //지도를 이동시키기 위한 객체 생성
        val loc2 = CameraUpdateFactory.newLatLngZoom(loc1, 15f)

        Log.d("MAP2", location.toString())

        // 이동
        googleMap.animateCamera(loc2)
    }

    //현재 위치를 측정하는 메소드
    private fun getMyLocation() {
        // 위치 정보를 관리하는 매니저 추출
        manager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // 저장되어 있는 위치값이 있으면 가져옴
        // 먼저 권한 체크부터 실행
        val auth1 = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        val auth2 = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)

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
        val auth1 = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        val auth2 = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)

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

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }
    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }
}