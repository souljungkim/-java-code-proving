package proving.basic

import org.junit.jupiter.api.Test

class Range {

    @Test
    void range(){
        assert [1,2] == 1..2
        assert ['a', 'b', 'c'] == 'a'..'c'
        assert ('ㄱ'..'ㄷ').size() == 7
        assert ('가'..'다').size() == 1765
        assert ['ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ'] == 'ㄱ'..'ㄷ'
    }


}
