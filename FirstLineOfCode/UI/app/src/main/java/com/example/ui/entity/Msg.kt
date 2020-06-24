package com.example.ui.entity

/**
 * The message entity
 *
 * @param content message content
 * @param type message type: 0 -> received, 1 -> sent
 */
class Msg(val content: String, val type: Int) {
    companion object {
        const val TYPE_RECEIVED = 0
        const val TYPE_SENT = 1
    }
}