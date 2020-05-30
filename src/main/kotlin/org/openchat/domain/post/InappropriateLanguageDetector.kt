package org.openchat.domain.post

class InappropriateLanguageDetector {
    private val inappropriateWords : List<String> = listOf("orange", "elephant", "ice cream")

    fun containsInappropriateWords(text: String): Boolean {
        return inappropriateWords
                .any { inappropriateWord -> text.toLowerCase().contains(inappropriateWord) }
    }
}
