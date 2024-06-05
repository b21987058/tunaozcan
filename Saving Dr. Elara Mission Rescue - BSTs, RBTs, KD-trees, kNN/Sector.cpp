#include "Sector.h"
#include <cmath>
#include <tuple>

// Constructor
Sector::Sector(int x, int y, int z) : x(x), y(y), z(z), left(nullptr), right(nullptr), parent(nullptr), color(RED) {
    // Calculate the Euclidean distance to the Earth
    distance_from_earth = std::sqrt(x*x + y*y + z*z);
    // Generate the sector code
    sector_code = std::to_string(static_cast<int>(std::floor(distance_from_earth))); // Distance component
    sector_code += x == 0 ? 'S' : (x > 0 ? 'R' : 'L'); // X coordinate
    sector_code += y == 0 ? 'S' : (y > 0 ? 'U' : 'D'); // Y coordinate
    sector_code += z == 0 ? 'S' : (z > 0 ? 'F' : 'B'); // Z coordinate
}

Sector::Sector(int x, int y, int z, bool color) : x(x), y(y), z(z), left(nullptr), right(nullptr), parent(nullptr), color(color) {
    // Calculate the Euclidean distance to the Earth
    distance_from_earth = std::sqrt(x*x + y*y + z*z);
    // Generate the sector code
    sector_code = std::to_string(static_cast<int>(std::floor(distance_from_earth))); // Distance component
    sector_code += x == 0 ? 'S' : (x > 0 ? 'R' : 'L'); // X coordinate
    sector_code += y == 0 ? 'S' : (y > 0 ? 'U' : 'D'); // Y coordinate
    sector_code += z == 0 ? 'S' : (z > 0 ? 'F' : 'B'); // Z coordinate
}

Sector::~Sector() {
    // No dynamic memory allocation in this class
}

Sector& Sector::operator=(const Sector& other) {
    if (this != &other) {
        x = other.x;
        y = other.y;
        z = other.z;
        sector_code = other.sector_code;
        distance_from_earth = other.distance_from_earth;
        left = other.left;
        right = other.right;
        // Assuming no dynamic memory allocation; otherwise, deep copy needed
    }
    return *this;
}

bool Sector::operator==(const Sector& other) const {
    return (x == other.x && y == other.y && z == other.z);
}

bool Sector::operator!=(const Sector& other) const {
    return !(*this == other);
}

std::tuple<int, int, int> Sector::extractCoordinatesFromCode(const std::string& sector_code) {
    int x = 0, y = 0, z = 0;
    int sign = 1; // Used to determine positive or negative coordinates

    // Skip the distance part (numeric characters at the beginning)
    size_t i = 0;
    while (i < sector_code.size() && std::isdigit(sector_code[i])) {
        ++i;
    }

    // Parse the directional symbols
    if (i < sector_code.size()) {
        x = (sector_code[i] == 'R') ? 1 : (sector_code[i] == 'L') ? -1 : 0;
        ++i;
    }
    if (i < sector_code.size()) {
        y = (sector_code[i] == 'U') ? 1 : (sector_code[i] == 'D') ? -1 : 0;
        ++i;
    }
    if (i < sector_code.size()) {
        z = (sector_code[i] == 'F') ? 1 : (sector_code[i] == 'B') ? -1 : 0;
    }

    return std::make_tuple(x, y, z);
}

bool Sector::isRed(Sector* node)
{
    if(node == nullptr) return false;
    return node->color;
}
