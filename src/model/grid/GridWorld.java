package model.grid;

import java.util.List;

import api.Grid;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This class manages all of the grids in the game
 *
 * @author Aninda Manocha, Daniel Chai
 */

@XStreamAlias("gridWorld")
public class GridWorld {

    @XStreamImplicit
    private List<Grid> myGrids;

    private String myMusicFile;

    public GridWorld(GridManager gridManager, String musicFile) {
        myGrids = gridManager.getGridList();
        myMusicFile = musicFile;
    }

    public List<Grid> getGrids() {
        return myGrids;
    }

    public String getMusicFile() {
        return myMusicFile;
    }
}