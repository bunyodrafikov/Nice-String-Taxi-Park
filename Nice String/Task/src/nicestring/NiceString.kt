package nicestring


fun String.isNice(): Boolean {
    val vowels = listOf<Char>('a', 'e', 'i', 'o', 'u')

    val DoesNotContainBuBaBe = !this.contains("bu") && !this.contains("ba") && !this.contains("be")
    val ContainsVowels = this.filter { it -> vowels.contains(it) }.count() >=3

    var ContainsDoubledChar = false
    this.forEachIndexed { index, element ->
        if (index != 0)
            if (this.get(index-1)==element){
                ContainsDoubledChar = true
            }
    }
    return listOf(DoesNotContainBuBaBe, ContainsVowels, ContainsDoubledChar)
        .filter { it }
        .count() >=2
}

