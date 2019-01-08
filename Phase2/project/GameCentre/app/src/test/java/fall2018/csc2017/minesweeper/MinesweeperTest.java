package fall2018.csc2017.minesweeper;

import org.junit.Test;

import static org.junit.Assert.*;

public class MinesweeperTest {


    GridManagerMinesweeper gridManagerMinesweeper = new GridManagerMinesweeper();

    @Test
    public void testSetterAndGetter() {
        assertEquals(10, gridManagerMinesweeper.getWIDTH());
        assertEquals(14, gridManagerMinesweeper.getHEIGHT());
        assertEquals(14, gridManagerMinesweeper.getBombNumber());
        assertFalse(gridManagerMinesweeper.isLost());
        gridManagerMinesweeper.setWIDTH(12);
        gridManagerMinesweeper.setHEIGHT(16);
        gridManagerMinesweeper.setBombNumber(20);
        gridManagerMinesweeper.setIsLost(true);
        assertEquals(12, gridManagerMinesweeper.getWIDTH());
        assertEquals(16, gridManagerMinesweeper.getHEIGHT());
        assertEquals(20, gridManagerMinesweeper.getBombNumber());
        assertTrue(gridManagerMinesweeper.isLost());
    }


    @Test
    public void testGetCellAt(){
        assertEquals(gridManagerMinesweeper.getCellAt(100), gridManagerMinesweeper.getCellAt(0, 10));
    }

    @Test
    public void testGenerator(){
        assertEquals(10, Generator.generate(14, 10, 14).length);
    }

}