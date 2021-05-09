package proving.basic

import org.junit.jupiter.api.Test


class Addition {

    @Test
    void addList(){
        List<Integer> numbers = [1,2,3]
        numbers << 4
        numbers << 5 << 6
        assert numbers.size() == 6
        assert numbers == [1,2,3,4,5,6]
    }

    @Test
    void addNumber(){
        assert (0 << 0) == 0
        assert (0 << 1) == 0
        assert (0 << 2) == 0
        assert (0 << 3) == 0

        assert (1 << 0) == 1
        assert (1 << 1) == 2
        assert (1 << 2) == 4
        assert (1 << 2 << 3) == 32

        assert (1 << 3) == 8
        assert (1 << 3 << 0) == 8
    }

    @Test
    void addString(){
        assert ("1" << "2") instanceof StringBuffer
        assert ("1" << "2").toString().equals("12")
    }

}
