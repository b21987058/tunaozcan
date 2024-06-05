#ifndef PA2_GAMECONTROLLER_H
#define PA2_GAMECONTROLLER_H

#include "BlockFall.h"

class GameController {
public:
    bool play(BlockFall &game, const string &commands_file);

private:
    void print_grid(BlockFall &game);            // Print the current state of the grid
    void rotate_right(BlockFall &game);           // Rotate the active block to the right (clockwise), if possible
    void rotate_left(BlockFall &game);            // Rotate the active block to the left (counterclockwise), if possible
    void move_right(BlockFall &game);             // Move the active block one space to the right, if possible
    void move_left(BlockFall &game);              // Move the active block one space to the left, if possible
    void drop(BlockFall &game);                   // Drop the active block, handling power-ups, clearing full rows, and gaining points
    void gravity_switch(BlockFall &game);         // Toggle gravity mode
    int initial_row;
    int initial_column;
};


#endif // PA2_GAMECONTROLLER_H