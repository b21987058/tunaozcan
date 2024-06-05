#ifndef SPACESECTORLLRBT_H
#define SPACESECTORLLRBT_H

#include "Sector.h"
#include <iostream>
#include <fstream>  
#include <sstream>
#include <vector>
#include <unordered_map>

class SpaceSectorLLRBT {
public:
    Sector* root;
    SpaceSectorLLRBT();
    ~SpaceSectorLLRBT();
    void readSectorsFromFile(const std::string& filename);
    void insertSectorByCoordinates(int x, int y, int z);
    void displaySectorsInOrder();
    void displaySectorsPreOrder();
    void displaySectorsPostOrder();
    std::vector<Sector*> getStellarPath(const std::string& sector_code);
    void printStellarPath(const std::vector<Sector*>& path);
private:
    std::unordered_map<std::string, Sector*> sectorMap;

    Sector* insertRecursive(Sector* node, int x, int y, int z);

    void displayInOrderRecursive(Sector* node);
    void displayPreOrderRecursive(Sector* node);
    void displayPostOrderRecursive(Sector* node);

    Sector* rotateLeft(Sector* node);
    Sector* rotateRight(Sector* node);
    void flipColors(Sector* node);
    bool shouldInsertLeft(Sector* node, int x, int y, int z);

    void deleteTree(Sector* node);

};

#endif // SPACESECTORLLRBT_H
