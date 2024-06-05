#ifndef SPACESECTORBST_H
#define SPACESECTORBST_H

#include <iostream>
#include <fstream>  
#include <sstream>
#include <unordered_map>
#include <vector>

#include "Sector.h"

class SpaceSectorBST {
  
public:
    Sector *root;
    SpaceSectorBST();
    ~SpaceSectorBST();
    void readSectorsFromFile(const std::string& filename); 
    void insertSectorByCoordinates(int x, int y, int z);
    void deleteSector(const std::string& sector_code);
    void displaySectorsInOrder();
    void displaySectorsPreOrder();
    void displaySectorsPostOrder();
    std::vector<Sector*> getStellarPath(const std::string& sector_code);
    void printStellarPath(const std::vector<Sector*>& path);
private:
    Sector* insertSectorRecursive(Sector* node, int x, int y, int z);
    void deleteLeafNode(Sector* node);
    void deleteNodeWithOneChild(Sector* node);
    void deleteNodeWithTwoChildren(Sector* node);
    void swapNodeValues(Sector* a, Sector* b);

    Sector* minValueNode(Sector* node);

    void deleteTreeRecursive(Sector* node);

    std::unordered_map<std::string, Sector*> sectorCodeToNodeMap;

    // Helper functions for recursive traversal
    void displayInOrderRecursive(Sector* node);
    void displayPreOrderRecursive(Sector* node);
    void displayPostOrderRecursive(Sector* node);

    void findStellarPathRecursive(Sector* node, int targetX, int targetY, int targetZ, std::vector<Sector*>& path);
    bool shouldGoLeft(Sector* node, int targetX, int targetY, int targetZ);
    bool isNodeOnPath(Sector* node, int targetX, int targetY, int targetZ);


};

#endif // SPACESECTORBST_H
