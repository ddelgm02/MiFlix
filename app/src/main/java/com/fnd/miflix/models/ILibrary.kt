package com.fnd.miflix.models

interface ILibrary {
    data class OnSearchQueryChange(val searchQuery: String) : ILibrary
    data class OnLibraryClick(val Library: Library) : ILibrary
    data class OnTabSelected(val index: Int) : ILibrary

}