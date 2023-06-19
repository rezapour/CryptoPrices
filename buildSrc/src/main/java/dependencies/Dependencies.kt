package dependencies

object Dependencies {

    const val hilt_android = "com.google.dagger:hilt-android:${Version.hilt}"
    const val dagger_hilt_compiler = "com.google.dagger:hilt-compiler:${Version.hilt}"

    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Version.fragment_ktx}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"

    const val okhttp = "com.squareup.okhttp3:okhttp:${Version.okhttp}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Version.okhttp}"

    const val kotlinx_coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutine}"
    const val kotlinx_coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutine}"

    const val lifecycle_viewmodel_ktx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycle}"
    const val lifecycle_livedata_ktx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Version.lifecycle}"
    const val lifecycle_runtime_ktx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycle}"

    const val navigation_fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Version.nav}"
    const val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Version.nav}"


    const val glide = "com.github.bumptech.glide:glide:${Version.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Version.glide}"

    const val room_runtime = "androidx.room:room-runtime:${Version.room}"
    const val room_runtime_ktx="androidx.room:room-ktx:${Version.room}"
    const val room_compiler = "androidx.room:room-compiler:${Version.room}"
    const val room_paging="androidx.room:room-paging:${Version.room}"

    const val glide_compose="com.github.bumptech.glide:compose:${Version.glide_compose}"

    const val navigation_compose="androidx.navigation:navigation-compose:${Version.nav_version}"
    const val hilt_navigation_compose="androidx.hilt:hilt-navigation-compose:${Version.nav_hilt}"

    const val paging_runtime="androidx.paging:paging-runtime:${Version.paging_runtime}"
    const val paging_compose="androidx.paging:paging-compose:${Version.paging_compose}"
}