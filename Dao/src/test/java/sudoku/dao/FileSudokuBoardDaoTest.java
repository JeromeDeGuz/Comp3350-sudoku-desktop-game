package sudoku.dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import sudoku.dao.factories.SudokuBoardDaoFactory;
import sudoku.dao.interfaces.Dao;
import sudoku.model.exceptions.FillingBoardSudokuException;
import sudoku.model.models.SudokuBoard;
import sudoku.model.solver.BacktrackingSudokuSolver;

public class FileSudokuBoardDaoTest {
    // Directory path for testing
    private static final String TEST_DIRECTORY = "test_dir";

    private void cleanUpDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && !file.getName().startsWith(".git")) {
                    file.delete();
                }
            }
        }
    }

    @AfterEach
    void tearDown() {
        cleanUpDirectory(new File(TEST_DIRECTORY));
    }

    @Test
    public void testWriteAndRead() {
        SudokuBoard sampleBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            sampleBoard.solveGame();
        } catch (FillingBoardSudokuException e) {
            e.printStackTrace();
        }

        sampleBoard.setField(0, 0, 5);
        sampleBoard.setField(0, 5, 9);
        sampleBoard.setField(4, 7, 2);

        final String boardName = "test_board";

        assertNotNull(sampleBoard);

        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory.createSudokuBoardDao(TEST_DIRECTORY)) {
            assertDoesNotThrow(() -> dao.write(boardName, sampleBoard));

            assertTrue(new File(TEST_DIRECTORY, boardName).exists());

            SudokuBoard readBoard = assertDoesNotThrow(() -> dao.read(boardName));
            assertNotNull(readBoard);

            assertEquals(sampleBoard, readBoard);

            assertEquals(sampleBoard.getField(0, 0), readBoard.getField(0, 0));
            assertEquals(sampleBoard.getField(0, 5), readBoard.getField(0, 5));
            assertEquals(sampleBoard.getField(4, 7), readBoard.getField(4, 7));

        } catch (Exception e) {
            Assertions.fail();
        }

    }

    @Test
    public void testNames() {
        //Assignment Section 4: Implement one automated test

        //arrange: set up the data
        SudokuBoard sampleBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        String name1 = "board1.sudoku";
        String name2 = "board2.sudoku";

        File dir = new File(TEST_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        
        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory.createSudokuBoardDao(TEST_DIRECTORY)) {
            //@before should make the list empty
            assertEquals(0, dao.names().size());  //check to make sure our cleaning was correct
            
            //by default the code in FileSudokuBoardDao.java:names() will save file names/
            //However, the function does not filter out .gitkeep file. Since our section 4 instructions was
            //to implement one automated test, not explicitly stating to change code implementation.
            //We will adjust to make our test understand that that file should be there when @before cleans up

            dao.write(name1, sampleBoard);
            dao.write(name2, sampleBoard);

            // act: call the function to test: dao.names()
            List<String> names = dao.names();

            // assert: result matches our testing data we arranged earlire
            assertEquals(2, names.size());  //we added 2 names
            assertTrue(names.contains(name1));  //verify contents
            assertTrue(names.contains(name2));

        } catch (Exception e) {
            fail(e);    //coping legacy codebase style
        }
    }

}
