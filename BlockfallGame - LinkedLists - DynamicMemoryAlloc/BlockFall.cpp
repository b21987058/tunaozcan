#include "BlockFall.h"
#include "BlockFall.h"
#include <fstream>
#include <sstream>
#include <iostream>
#include <iterator>
#include <vector>
#include "Leaderboard.h"
#include <algorithm>
BlockFall::BlockFall(string grid_file_name, string blocks_file_name, bool gravity_mode_on, const string &leaderboard_file_name, const string &player_name) : gravity_mode_on(
                                                                                                                                                                 gravity_mode_on),
                                                                                                                                                             leaderboard_file_name(leaderboard_file_name), player_name(player_name)
{
    initialize_grid(grid_file_name);
    read_blocks(blocks_file_name);
    leaderboard.read_from_file(leaderboard_file_name);
}
vector<vector<bool>> rotate_clockwise(const vector<vector<bool>> &matrix)
{
    int rows = matrix.size();
    int cols = matrix[0].size();

    // Yeni matrisi oluştur ve boyutlarını döndür
    vector<vector<bool>> rotated(cols, vector<bool>(rows, false));

    for (int i = 0; i < rows; ++i)
    {
        for (int j = 0; j < cols; ++j)
        {
            rotated[j][rows - 1 - i] = matrix[i][j];
        }
    }

    return rotated;
}
void BlockFall::read_blocks(const string &input_file)
{
    vector<vector<bool>> block_shape;
    ifstream file(input_file);
    bool is_finito = false;
    if (file.is_open())
    {
        string line;

        while (getline(file, line))
        {
            // Assuming the line contains the block representation in the format [..]
            if (line.empty() || line[0] == '#')
            {
                continue;
            }

            // Parse the line to get the block's shape
            for (char c : line)
            {
                if (c == '0' || c == '1')
                {
                    if (block_shape.empty())
                    {
                        block_shape.push_back({}); // If block_shape is empty, add a new vector
                    }
                    block_shape.back().push_back(c == '1');
                }
                else if (c == '[')
                {
                    block_shape.push_back({});
                }
                else if (c == ']')
                {
                    is_finito = true;
                }
            }
            if (is_finito == false)
            {
                block_shape.push_back({});
                continue;
            }
            else
            {
                Block *new_block = new Block();
                new_block->shape = block_shape;

                // Add rotations for the new block
                Block *current_rotation = new_block;
                for (int i = 1; i < 4; ++i)
                {
                    current_rotation->right_rotation = new Block();
                    current_rotation->right_rotation->shape = rotate_clockwise(current_rotation->shape);
                    current_rotation->right_rotation->left_rotation = current_rotation;
                    current_rotation = current_rotation->right_rotation;
                }

                // Add the new block to the linked list
                if (initial_block == nullptr)
                {
                    initial_block = new_block;
                    active_rotation = new_block; // Set active_rotation to initial_block initially
                }
                else
                {
                    active_rotation->next_block = new_block;
                    active_rotation = new_block;
                }

                // Clear block_shape and reset is_finito
                for (auto &innerVector : block_shape)
                {
                    innerVector.clear();
                }
                block_shape.clear();
                is_finito = false;
            }
        }

        // Set active_rotation to initial_block at the end
        active_rotation = initial_block;

        Block *last_block = initial_block;
        while (last_block->next_block != nullptr)
        {
            last_block = last_block->next_block;
        }

        power_up = last_block->shape;
        // print_blocks();
        // print_rotated_blocks();
        file.close();
    }
    else
    {
        cerr << "Error opening file: " << input_file << endl;
    }
if (active_rotation != nullptr)
    {
        // Kullanılan bloğun grid içindeki konumunu al
        int blockRow = active_rotation->left_top_row;
        int blockCol = active_rotation->left_top_column;

        // Yatay uzunluğu ve dikey uzunluğu bul
        int blockHeight = active_rotation->shape.size();
        int blockWidth = active_rotation->shape[0].size();

               for (int i = 0; i < blockHeight; ++i)
            {
                for (int j = 0; j < blockWidth; ++j)
                {
                    if (active_rotation->shape[i][j])
                    {
                        grid[blockRow + i][blockCol + j] = 1;
                    }
                }
            }
            }
}

void BlockFall::initialize_grid(const string &input_file)
{
    ifstream file(input_file);

    if (file.is_open())
    {
        string line;

        // Read the file to determine the number of rows and columns
        rows = 0;
        cols = 0;

        while (getline(file, line))
        {
            // Check if the line is empty or a comment (optional)
            if (line.empty() || line[0] == '#')
            {
                continue;
            }

            ++rows;

            // Assuming all rows have the same number of columns
            if (cols == 0)
            {
                istringstream iss(line);
                cols = distance(istream_iterator<char>(iss), istream_iterator<char>());
            }
        }

        // Initialize the "grid" member variable
        file.clear();
        file.seekg(0, ios::beg);

        grid.resize(rows, vector<int>(cols, 0));

        // Read the file to populate the grid
        for (int i = 0; i < rows; ++i)
        {
            getline(file, line);

            // Parse the line and populate the grid
            istringstream iss(line);
            for (int j = 0; j < cols; ++j)
            {
                iss >> grid[i][j];
            }
        }

        file.close();
       
    }
    else
    {
        cerr << "Error opening file: " << input_file << endl;
    }
}

void BlockFall::print_initial_grid()
{
    std::cout << "Initial Grid:\n";

    for (int i = 0; i < rows; ++i)
    {
        for (int j = 0; j < cols; ++j)
        {
            std::cout << grid[i][j] << " ";
        }
        std::cout << '\n';
    }
}
void BlockFall::print_initial_blocks()
{
    Block *current_block = initial_block;

    while (current_block != nullptr)
    {
        cout << "Initial Block Shape:" << endl;
        for (const auto &row : current_block->right_rotation->shape)
        {
            for (bool cell : row)
            {
                cout << (cell ? "1" : "0") << " ";
            }
            cout << endl;
        }

        cout << "Rotation Status: ";
        if (current_block == active_rotation)
        {
            cout << "Active Rotation";
        }
        else
        {
            cout << "Inactive Rotation";
        }
        cout << endl
             << endl;

        current_block = current_block->next_block;
    }
}

void BlockFall::print_blocks()
{
    Block *current_block = initial_block;

    while (current_block != nullptr)
    {
        cout << "Block Shape:" << endl;
        for (const auto &row : current_block->shape)
        {
            for (bool cell : row)
            {
                cout << (cell ? "1" : "0") << " ";
            }
            cout << endl;
        }

        cout << "Rotation Status: ";
        if (current_block == active_rotation)
        {
            cout << "Active Rotation";
        }
        else
        {
            cout << "Inactive Rotation";
        }
        cout << endl
             << endl;

        current_block = current_block->next_block;
    }
}
BlockFall::~BlockFall()
{
    // Free dynamically allocated memory used for storing game blocks
    Block *current_block = initial_block;
    Block *next_block = nullptr;

    // Traverse the linked list and delete each block
    while (current_block != nullptr)
    {
        next_block = current_block->next_block;

        // Free the dynamically allocated memory for the shape vector
        for (auto &row : current_block->shape)
        {
            row.clear();
        }
        current_block->shape.clear();

        // Free the dynamically allocated memory for the block itself
        delete current_block;

        current_block = next_block;
    }
}
void BlockFall::print_rotated_blocks()
{
    Block *current_block = initial_block;
    Block *current_block2 = initial_block;
    while (current_block2 != nullptr)
    {
        current_block = current_block2;
        for (int rotation = 0; rotation < 4; ++rotation)
        {

            cout << "Block Shape (Rotation " << rotation << "):" << endl;
            for (const auto &row : current_block->shape)
            {
                for (bool cell : row)
                {
                    cout << (cell ? "1" : "0") << " ";
                }
                cout << endl;
            }

            cout << endl;
            current_block = current_block->right_rotation;
        }
        current_block2 = current_block2->next_block;
    }
}
void BlockFall::move_right()
{
    // Aktif rotasyondaki bloğu sağa kaydır
    if (active_rotation != nullptr)
    {
        // Kullanılan bloğun grid içindeki konumunu al
        int blockRow = active_rotation->left_top_row;
        int blockCol = active_rotation->left_top_column;

        // Yatay uzunluğu ve dikey uzunluğu bul
        int blockHeight = active_rotation->shape.size();
        int blockWidth = active_rotation->shape[0].size();

        // Bloğun sağa kaydırılabilir olup olmadığını kontrol et

        if (blockCol + blockWidth < cols)
        {
            // Bloğun bulunduğu hücreleri temizle
            if (checkMove(true, blockCol, blockWidth, blockHeight))
            {
                for (int i = 0; i < blockHeight; ++i)
                {
                    for (int j = 0; j < blockWidth; ++j)
                    {
                        if (active_rotation->shape[i][j])
                        {
                            grid[blockRow + i][blockCol + j] = 0;
                        }
                    }
                }

                // Bloğu sağa kaydır
                active_rotation->left_top_column += 1;

                // Yeni konumda bloğu çiz
                for (int i = 0; i < blockHeight; ++i)
                {
                    for (int j = 0; j < blockWidth; ++j)
                    {
                        if (active_rotation->shape[i][j])
                        {
                            grid[blockRow + i][blockCol + j + 1] = 1;
                        }
                    }
                }
            }
        }
    }
}
void BlockFall::move_left()
{
    // Aktif rotasyondaki bloğu sola kaydır
    if (active_rotation != nullptr)
    {
        // Kullanılan bloğun grid içindeki konumunu al
        int blockRow = active_rotation->left_top_row;
        int blockCol = active_rotation->left_top_column;

        // Yatay uzunluğu ve dikey uzunluğu bul
        int blockHeight = active_rotation->shape.size();
        int blockWidth = active_rotation->shape[0].size();

        // Bloğun sola kaydırılabilir olup olmadığını kontrol et
        if (blockCol > 0)
        {
            // Bloğun bulunduğu hücreleri temizle
            for (int i = 0; i < blockHeight; ++i)
            {
                for (int j = 0; j < blockWidth; ++j)
                {
                    if (active_rotation->shape[i][j])
                    {
                        grid[blockRow + i][blockCol + j] = 0;
                    }
                }
            }

            // Bloğu sola kaydır
            active_rotation->left_top_column -= 1;

            // Yeni konumda bloğu çiz
            for (int i = 0; i < blockHeight; ++i)
            {
                for (int j = 0; j < blockWidth; ++j)
                {
                    if (active_rotation->shape[i][j])
                    {
                        grid[blockRow + i][blockCol + j - 1] = 1;
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i < blockHeight; ++i)
            {
                for (int j = 0; j < blockWidth; ++j)
                {
                    if (active_rotation->shape[i][j])
                    {
                        grid[blockRow + i][blockCol + j] = 1;
                    }
                }
            }
        }
    }
}
bool BlockFall::can_move_right() const
{
    // Eğer active_rotation null değilse (yani bir blok varsa)
    if (active_rotation != nullptr)
    {
        // Bloğun sağ üst köşesinin sütun indeksini al
        int current_col = active_rotation->left_top_column;

        // Geçici olarak sağa kaydır
        ++current_col;

        // Bloğun sağ üst köşesinin satır indeksini al
        int current_row = active_rotation->left_top_row;

        // Geçici konumu kontrol et
        // Koşulları oyun mantığınıza göre güncelleyebilirsiniz
        if (current_col < cols && grid[current_row][current_col] == 0)
        {
            active_rotation->left_top_column++;
            return true;
        }
    }

    // Eğer bir blok yoksa veya sağa hareket mümkün değilse false döndür
    return false;
}
bool BlockFall::checkMove(bool rL, int block_col, int block_width, int blockHeight)
{
    if (rL)
    {
        for (int i = 0; i < blockHeight; i++)
        {
            if (grid[i][block_col + block_width] == 1)
            {
                return false;
            }
        }
        return true;
    }

    return false;
}
void BlockFall::rotate_right()
{
    if (active_rotation != nullptr)
    {
        // Kullanılan bloğun grid içindeki konumunu al
        int blockRow = active_rotation->left_top_row;
        int blockCol = active_rotation->left_top_column;

        // Yatay uzunluğu ve dikey uzunluğu bul
        int blockHeight = active_rotation->shape.size();
        int blockWidth = active_rotation->shape[0].size();
        for (int i = 0; i < blockHeight; ++i)
        {
            for (int j = 0; j < blockWidth; ++j)
            {
                if (active_rotation->shape[i][j])
                {
                    grid[blockRow + i][blockCol + j] = 1;
                }
            }
        }
        removeBlockFromGrid();
        if (check_Rotate(true))
        {
            active_rotation->right_rotation->left_top_row = blockRow;
            active_rotation->right_rotation->left_top_column = blockCol;
            int blockRow2 = active_rotation->right_rotation->left_top_row;
            int blockCol2 = active_rotation->right_rotation->left_top_column;

            // Yatay uzunluğu ve dikey uzunluğu bul
            int blockHeight2 = active_rotation->right_rotation->shape.size();
            int blockWidth2 = active_rotation->right_rotation->shape[0].size();
            active_rotation = active_rotation->right_rotation;
            for (int i = 0; i < blockHeight2; i++)
            {
                for (int j = 0; j < blockWidth2; j++)
                {

                    if (active_rotation->shape[i][j])
                    {
                        grid[blockRow2 + i][blockCol2 + j] = 1;
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i < blockHeight; ++i)
            {
                for (int j = 0; j < blockWidth; ++j)
                {
                    if (active_rotation->shape[i][j])
                    {
                        grid[blockRow + i][blockCol + j] = 1;
                    }
                }
            }
        }
    }
}
void BlockFall::removeBlockFromGrid()
{
    // Kullanılan bloğun grid içindeki konumunu al
    int blockRow = active_rotation->left_top_row;
    int blockCol = active_rotation->left_top_column;

    // Yatay uzunluğu ve dikey uzunluğu bul
    int blockHeight = active_rotation->shape.size();
    int blockWidth = active_rotation->shape[0].size();

    // Bloğu grid üzerinden kaldır
    for (int i = 0; i < blockHeight; ++i)
    {
        for (int j = 0; j < blockWidth; ++j)
        {
            if (active_rotation->shape[i][j])
            {
                grid[blockRow + i][blockCol + j] = 0;
            }
        }
    }
}
bool BlockFall::check_Rotate(bool rL)
{
    if (active_rotation == nullptr)
    {
        return false;
    }

    // Kullanılan bloğun grid içindeki konumunu al
    int blockRow = active_rotation->left_top_row;
    int blockCol = active_rotation->left_top_column;

    // Yatay uzunluğu ve dikey uzunluğu bul
    int blockHeight = active_rotation->shape.size();
    int blockWidth = active_rotation->shape[0].size();
    if (rL)
    {

        int blockRow2 = active_rotation->right_rotation->left_top_row;
        int blockCol2 = active_rotation->right_rotation->left_top_column;

        // Yatay uzunluğu ve dikey uzunluğu bul
        int blockHeight2 = active_rotation->right_rotation->shape.size();
        int blockWidth2 = active_rotation->right_rotation->shape[0].size();
        if ((blockCol + blockWidth2 >= cols))
        {
            return false;
        }
        for (int i = 0; i < blockHeight2; i++)
        {
            for (int j = 0; j < blockWidth2; j++)
            {

                if (grid[blockRow + i][blockCol + j] == 1)
                {
                    return false;
                }
            }
        }

        return true;
    }
    else
    {
        int blockRow2 = active_rotation->left_rotation->left_top_row;
        int blockCol2 = active_rotation->left_rotation->left_top_column;

        // Yatay uzunluğu ve dikey uzunluğu bul
        int blockHeight2 = active_rotation->left_rotation->shape.size();
        int blockWidth2 = active_rotation->left_rotation->shape[0].size();
        // if (blockCol == 0){
        //   return false;
        //}
        for (int i = 0; i < blockHeight2; i++)
        {
            for (int j = 0; j < blockWidth2; j++)
            {

                if (grid[blockRow2 + i][blockCol2 + j] == 1)
                {
                    return false;
                }
            }
        }
        return true;
    }
    return false;
}
void BlockFall::rotate_left()
{
    if (active_rotation != nullptr)
    {
        // Kullanılan bloğun grid içindeki konumunu al
        int blockRow = active_rotation->left_top_row;
        int blockCol = active_rotation->left_top_column;

        // Yatay uzunluğu ve dikey uzunluğu bul
        int blockHeight = active_rotation->shape.size();
        int blockWidth = active_rotation->shape[0].size();
        for (int i = 0; i < blockHeight; ++i)
        {
            for (int j = 0; j < blockWidth; ++j)
            {
                if (active_rotation->shape[i][j])
                {
                    grid[blockRow + i][blockCol + j] = 1;
                }
            }
        }
        removeBlockFromGrid();
        if (check_Rotate(true))
        {
            active_rotation->left_rotation->left_top_row = blockRow;
            active_rotation->left_rotation->left_top_column = blockCol;
            int blockRow2 = active_rotation->left_rotation->left_top_row;
            int blockCol2 = active_rotation->left_rotation->left_top_column;

            // Yatay uzunluğu ve dikey uzunluğu bul
            int blockHeight2 = active_rotation->left_rotation->shape.size();
            int blockWidth2 = active_rotation->left_rotation->shape[0].size();
            active_rotation = active_rotation->left_rotation;
            for (int i = 0; i < blockHeight2; i++)
            {
                for (int j = 0; j < blockWidth2; j++)
                {

                    if (active_rotation->shape[i][j])
                    {
                        grid[blockRow2 + i][blockCol2 + j] = 1;
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i < blockHeight; ++i)
            {
                for (int j = 0; j < blockWidth; ++j)
                {
                    if (active_rotation->shape[i][j])
                    {
                        grid[blockRow + i][blockCol + j] = 1;
                    }
                }
            }
        }
    }
}
void BlockFall::drop()
{
    if (gravity_mode_on)
    {
        gravity_on();
    }
    else
    {
        gravity_off();
    }
    if (active_rotation != nullptr)
    {
        // Kullanılan bloğun grid içindeki konumunu al
        int blockRow = active_rotation->left_top_row;
        int blockCol = active_rotation->left_top_column;

        // Yatay uzunluğu ve dikey uzunluğu bul
        int blockHeight = active_rotation->shape.size();
        int blockWidth = active_rotation->shape[0].size();

               for (int i = 0; i < blockHeight; ++i)
            {
                for (int j = 0; j < blockWidth; ++j)
                {
                    if (active_rotation->shape[i][j])
                    {
                        grid[blockRow + i][blockCol + j] = 1;
                    }
                }
            }
            }
    else{
        gameOver2();
    }
}
void BlockFall::gameOver2(){
    std::cout <<"YOU WIN!" << std::endl;
    std::cout << "No more blocks." << std::endl;
    std::cout << "Final grid and score:" << std::endl;

    std::cout << "" << std::endl;
    printGrid();
    leaderboard.print_leaderboard();
}
void BlockFall::gravity_on()
{
}
void BlockFall::increaseScoreForBlockDrop(int occupiedCells, int fallDistance) {
        score += occupiedCells * fallDistance;
    }

void BlockFall:: calculateLines(){
    
}

void BlockFall::gravity_off()
{
    // Aktif rotasyondaki bloğu bırak
    int fallDistance=0;
    int occupiedCells=0;
    
    if (active_rotation != nullptr)
    {
        // Kullanılan bloğun grid içindeki konumunu al
        
        int blockRow = active_rotation->left_top_row;
        int blockCol = active_rotation->left_top_column;
        cout << "row : " << active_rotation->left_top_row << endl;
        cout << "Cols : " << active_rotation->left_top_column << endl;
        // Yatay uzunluğu ve dikey uzunluğu bul
        int blockHeight = active_rotation->shape.size();
        int blockWidth = active_rotation->shape[0].size();

        for (int i = 0; i < blockHeight; ++i)
            {
                for (int j = 0; j < blockWidth; ++j)
                {
                    if (active_rotation->shape[i][j])
                    {
                        occupiedCells++;
                    }
                }
            }
        while (blockRow + blockHeight < grid.size() && canMoveDown(grid, blockRow, blockCol, blockHeight, blockWidth))
        {
            // Bloğu aşağı bırak
            for (int i = blockRow + blockHeight - 1; i >= blockRow; --i)
            {
                for (int j = blockCol; j < blockCol + blockWidth; ++j)
                {
                    if (grid[i][j] == 1 && grid[i + 1][j] == 0)
                    {
                        // Bloğun bulunduğu hücreleri temizle
                        grid[i][j] = 0;
                        // Bloğu bir alt satıra kaydır
                        grid[i + 1][j] = 1;
                    }
                }
            }

            // Bloğu bir birim aşağı kaydır
            ++blockRow;
        }
        fallDistance=blockRow;
        increaseScoreForBlockDrop(occupiedCells,fallDistance);
        active_rotation = active_rotation->next_block;
    }
}
bool BlockFall::canMoveDown(const vector<vector<int>>& grid, int row, int col, int blockHeight, int blockWidth) {
    // Bloğun düşüp düşemeyeceğini kontrol et
   for (int j = col; j < col + blockWidth; ++j) {
        if (row + blockHeight < grid.size() && grid[row + blockHeight][j] == 1) {
            return false;
        }
    }
    return true;
}
void BlockFall::printGrid(){
    int highScore=leaderboard.getHighScore();
    std::cout << "Score: " << score << std::endl;
    std::cout << "High Score: " << highScore << std::endl;
        for (int i = 0; i < rows; ++i)
    {
        for (int j = 0; j < cols; ++j)
        {
            std::cout << grid[i][j] << " ";
        }
        std::cout << '\n';
    }
}
void BlockFall::endGame(){
    std::cout <<"GAME FINISHED!" << std::endl;
    std::cout << "No more commands." << std::endl;
    std::cout << "Final grid and score:" << std::endl;
    std::cout << "" << std::endl;
    printGrid();
    leaderboard.print_leaderboard();
}
/*int main()
{
    // Örnek dosya isimleri, dilediğiniz dosya isimlerini kullanabilirsiniz
    string grid_file_name = "sampleIO//1_big_grid_gravity_switch/grid.dat";
    string blocks_file_name = "sampleIO//1_big_grid_gravity_switch/blocks.dat";
    string leaderboard_file_name = "leaderboard.txt";
    string player_name = "Player1";

    // Yeni BlockFall nesnesi oluştur
    BlockFall game(grid_file_name, blocks_file_name, false, leaderboard_file_name, player_name);
    cout << "aaaaa" << endl;
    // game.print_initial_blocks();
    cout << "zzzzzzzzzzz" << endl;

    // Oyunu başlat ve geliştirmelerinizi yapın
    // Örneğin, oyun döngüsü, kullanıcı girişi almak, blokları düşürmek, lider tabloyu güncellemek, vb.
    // Sağa hareket ettir
    game.print_initial_grid();
    game.move_left();
    game.move_left();
    game.print_initial_grid();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.move_right();
    game.print_initial_grid();

    game.rotate_right();
    game.move_right();

    game.print_initial_grid();
    game.rotate_right();
    //
    // Güncellenmiş durumu ekrana yazdır
    game.print_initial_grid();

    cout << "Lasto" << endl;
    // Güncellenmiş durumu ekrana yazdır
    game.print_initial_grid();
    game.move_left();
    game.move_left();
    game.move_left();
    game.move_left();
    game.move_left();
    game.print_initial_grid();

    // game.print_initial_grid();
    game.rotate_left();
    game.print_initial_grid();
    game.drop();
    game.print_initial_grid();

    // game.print_blocks();
    return 0;
}*/
