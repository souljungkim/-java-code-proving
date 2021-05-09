package proving.basic

import org.junit.jupiter.api.Test

class Each {


    @Test
    void each(){
        int sum = 0
        [1,2,3,4,5].each{sum += it }

        assert sum == [1,2,3,4,5].sum()
    }

    @Test
    void eachWithIndex(){
        int sum = 0
        [1,2,3,4,5].each{sum += it }

        assert sum == [1,2,3,4,5].sum()
    }

    @Test
    void find(){
        int sum = 0
        [1,2,3,4,5].each{sum += it }

        assert sum == [1,2,3,4,5].sum()
    }


    @Test
    void findAll(){
        int sum = 0
        [1,2,3,4,5].each{sum += it }

        assert sum == [1,2,3,4,5].sum()
    }

    @Test
    void collect(){
        int sum = 0
        [1,2,3,4,5].each{sum += it }

        assert sum == [1,2,3,4,5].sum()
    }

    @Test
    void collectEntries(){
    }

    @Test
    void any(){
    }

    @Test
    void every(){
    }

    @Test
    void sum(){
    }

    @Test
    void groupby(){
    }

}
