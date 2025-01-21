package com.fnd.miflix.controller

import com.fnd.miflix.models.Library

data class LibraryController(
    val searchQuery: String,
    val searchResults: List<Library> = emptyList(),
    val favoriteLibrary: List<Library> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: String? = null
    )
