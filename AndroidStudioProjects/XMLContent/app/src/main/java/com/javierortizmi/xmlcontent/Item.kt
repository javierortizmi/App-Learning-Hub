package com.javierortizmi.xmlcontent

class Item(newTitle: String?, newLink: String?) {
    private var title: String? = null
    private var link: String? = null

    init {
        setTitle(newTitle)
        setLink(newLink)
    }

    fun setTitle(newTitle: String?) {
        title = newTitle
    }

    fun setLink(newLink: String?) {
        link = newLink
    }

    fun getTitle(): String? {
        return title
    }

    /* fun getLink(): String? {
        return link
    } */

    override fun toString(): String {
        return "$title; $link"
    }

}