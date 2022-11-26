package com.example.umc.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class UserFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentUserBinding

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
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
        val mapFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?

        mapFragment?.getMapAsync(this) //비동기적으로 구글 맵 실행
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
        getMyLocation()
    }
}