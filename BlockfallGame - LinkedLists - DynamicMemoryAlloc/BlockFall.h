#ifndef BLOCKFALL_H
#define BLOCKFALL_H

#define occupiedCellChar "██"
#define unoccupiedCellChar "▒▒"

#include <vector>
#include <string>

#include "Block.h"
#include "LeaderboardEntry.h"
#include "Leaderboard.h"

using namespace std;

class BlockFall {
public:

    BlockFall(string grid_file_name, string blocks_file_name, bool gravity_mode_on, const string &leaderboard_file_name,
              const string &player_name);
    virtual ~BlockFall();
    int score;
    int rows;  // Number of rows in the grid
    int cols;  // Number of columns in the grid
    vector<vector<int> > grid;  // 2D game grid
    vector<vector<bool>> power_up; // 2D matrix of the power-up shape
    Block * initial_block = nullptr; // Head of the list of game blocks. Must be filled up and initialized after a call to read_blocks()
    Block * active_rotation = nullptr; // Currently active rotation of the active block. Must start with the initial_block
    bool gravity_mode_on = false; // Gravity mode of the game
    unsigned long current_score = 0; // Current score of the game
    string leaderboard_file_name; // Leaderboard file name, taken from the command-line argument 5 in main
    string player_name; // Player name, taken from the command-line argument 6 in main
    //Leaderboard leaderboard;
    void print_initial_grid();
    void print_blocks();
    bool checkMove(bool rL,int block_col, int block_width,int blockHeight);
    void print_initial_blocks();
    void print_rotated_blocks();
    void initialize_grid(const string & input_file); // Initializes the grid using the command-line argument 1 in main
    void read_blocks(const string & input_file); // Reads the input file and calls the read_block() function for each block;
    bool can_move_right() const;
    void move_right();
    void move_left();
    void rotate_right();
    void rotate_left();
    bool check_Rotate(bool rL);
    void removeBlockFromGrid();
    void drop();
    void gravity_on();
    void gravity_off();
    void calculateLines();
    void endGame();
    bool isGridFull();
    void printGrid();
    bool canMoveDown(const vector<vector<int>>& grid, int row, int col, int blockHeight, int blockWidth);
    void increaseScoreForBlockDrop(int occupiedCells, int fallDistance);
    void gameOver2();
};  



#endif // BLOCKFALL_H
