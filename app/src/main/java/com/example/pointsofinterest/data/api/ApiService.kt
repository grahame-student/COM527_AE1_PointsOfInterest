package com.example.pointsofinterest.data.api

class ApiService {
    companion object {
        private var instance: PoiApiService? = null

        fun getPoiServiceInstance(): PoiApiService {
            var tmpInstance = instance
            if (tmpInstance == null) {
                tmpInstance = RemoteDataSource()
                instance = tmpInstance
            }
            return tmpInstance
        }
    }
}
