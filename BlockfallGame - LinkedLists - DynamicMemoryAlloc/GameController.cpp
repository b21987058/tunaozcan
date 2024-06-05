#include "GameController.h"

#include <fstream>
#include <iostream>

bool GameController::play(BlockFall &game, const string &commands_file) {
    initial_row=0;
    initial_column=0;
    ifstream file(commands_file);

    if (file.is_open()) {
        string line;

        while (getline(file, line)) {
            // Check if the line is empty or a comment
            if (line.empty() || line[0] == '#') {
                continue;
            }

            // Parse the command and perform the corresponding action
            if (line == "PRINT_GRID") {
                print_grid(game);
            } else if (line == "ROTATE_RIGHT") {
                rotate_right(game);
            } else if (line == "ROTATE_LEFT") {
                rotate_left(game);
            } else if (line == "MOVE_RIGHT") {
                move_right(game);
            } else if (line == "MOVE_LEFT") {
                move_left(game);
            } else if (line == "DROP") {
                drop(game);
            } else if (line == "GRAVITY_SWITCH") {
                gravity_switch(game);
            }else if(line.empty()){
                game.endGame();
            } 
            else {
                cout << "Unknown command: " << line << endl;
            }
        }

        file.close();
        return true;
    } else {
        cerr << "Error opening file: " << commands_file << endl;
        return false;
    }
}

void GameController::print_grid(BlockFall &game) {
    // Call the corresponding function in BlockFall to print the grid
    game.print_initial_grid();
}

void GameController::rotate_right(BlockFall &game) {
    
        game.rotate_right();
    
   
}

void GameController::rotate_left(BlockFall &game) {
        
        game.rotate_left();
    }


void GameController::move_right(BlockFall &game) {

    game.move_right();
}

void GameController::move_left(BlockFall &game) {

    game.move_left();
}

void GameController::drop(BlockFall &game) {
    // Call the corresponding function in BlockFall to drop the active block
    game.drop();
}

void GameController::gravity_switch(BlockFall &game) {
    // Call the corresponding function in BlockFall to toggle gravity mode
    game.gravity_mode_on=!game.gravity_mode_on;
    
}

