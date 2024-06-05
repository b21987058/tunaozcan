#include "SpaceSectorBST.h"

using namespace std;

SpaceSectorBST::SpaceSectorBST() : root(nullptr) {}

SpaceSectorBST::~SpaceSectorBST() {
    // Free any dynamically allocated memory in this class.
    deleteTreeRecursive(root);
}

void SpaceSectorBST::deleteTreeRecursive(Sector* node) {
    if (node != nullptr) {
        deleteTreeRecursive(node->left);
        deleteTreeRecursive(node->right);
        delete node;
    }
}

void SpaceSectorBST::readSectorsFromFile(const std::string& filename) {
    std::ifstream file(filename);
    std::string line;
    int x, y, z;

    // Skip the header line
    std::getline(file, line);

    while (std::getline(file, line)) {
        std::istringstream iss(line);
        if (iss >> x && iss.ignore() && iss >> y && iss.ignore() && iss >> z) {
            insertSectorByCoordinates(x, y, z);
        }
    }
}

void SpaceSectorBST::insertSectorByCoordinates(int x, int y, int z) {
    // Instantiate and insert a new sector into the space sector BST map according to the 
    // coordinates-based comparison criteria.
    root = insertSectorRecursive(root, x, y, z);
}

Sector* SpaceSectorBST::insertSectorRecursive(Sector* node, int x, int y, int z) {
    // Base case: If the node is null, create a new node
    if (!node) {
        Sector* new_sector = new Sector(x, y, z);
        sectorCodeToNodeMap[new_sector->sector_code] = new_sector;
        return new_sector;
    }

    // Decide whether to go left or right
    if (x < node->x || (x == node->x && (y < node->y || (y == node->y && z < node->z)))) {
        node->left = insertSectorRecursive(node->left, x, y, z);
        node->left->parent = node;
    } else {
        node->right = insertSectorRecursive(node->right, x, y, z);
        node->right->parent = node;
    }

    return node;
}

void SpaceSectorBST::deleteSector(const std::string& sector_code) {
    // TODO: Delete the sector given by its sector_code from the BST.
    auto targetNodeIter = sectorCodeToNodeMap.find(sector_code);
    if (targetNodeIter == sectorCodeToNodeMap.end()) {
        // Sector code not found
        return;
    }

    Sector* nodeToDelete = sectorCodeToNodeMap[sector_code];
    if (!nodeToDelete) return; // Node not found

    if (!nodeToDelete->left && !nodeToDelete->right) {
        // Node is a leaf
        deleteLeafNode(nodeToDelete);
    } else if (!nodeToDelete->left || !nodeToDelete->right) {
        // Node has one child
        deleteNodeWithOneChild(nodeToDelete);
    } else {
        // Node has two children
        deleteNodeWithTwoChildren(nodeToDelete);
    }

    sectorCodeToNodeMap.erase(sector_code);
}

Sector* SpaceSectorBST::minValueNode(Sector* node) {
    Sector* current = node;
    while (current && current->left != nullptr) {
        current = current->left;
    }
    return current;
}

void SpaceSectorBST::deleteLeafNode(Sector* node) {
    if (node == nullptr) return;

    // If the leaf node is not the root of the tree
    if (node->parent != nullptr) {
        // Determine if the leaf node is a left or right child and set the parent's pointer to nullptr
        if (node->parent->left == node) {
            node->parent->left = nullptr;
        } else if (node->parent->right == node) {
            node->parent->right = nullptr;
        }
    } else {
        // If the leaf node is the root, update the root pointer
        this->root = nullptr;
    }

    delete node; // Free the memory
}

void SpaceSectorBST::deleteNodeWithOneChild(Sector* node) {
    if (node == nullptr) return;

    // Determine the child (either left or right)
    Sector* child = node->left ? node->left : node->right;
    if (node->parent != nullptr) {
        // Connect the child to the parent of the node
        if (node->parent->left == node) {
            node->parent->left = child;
        } else if (node->parent->right == node) {
            node->parent->right = child;
        }

        // Update the parent pointer of the child
        if (child != nullptr) {
            child->parent = node->parent;
        }
    } else {
        // If the node is the root, update the root pointer
        this->root = child;
        if (child != nullptr) {
            child->parent = nullptr;
        }
    }

    delete node; // Free the memory
}

void SpaceSectorBST::deleteNodeWithTwoChildren(Sector* node) {
    // Find in-order successor
    Sector* successor = minValueNode(node->right);

    // Swap values with successor
    swapNodeValues(node, successor);

    // Delete the successor
    if (successor->left || successor->right) {
        deleteNodeWithOneChild(successor);
    } else {
        deleteLeafNode(successor);
    }
}

void SpaceSectorBST::swapNodeValues(Sector* a, Sector* b) {
    // Swap the values of a and b
    std::swap(a->x, b->x);
    std::swap(a->y, b->y);
    std::swap(a->z, b->z);
    std::swap(a->distance_from_earth, b->distance_from_earth);
    std::swap(a->sector_code, b->sector_code);
    // Update the map
    sectorCodeToNodeMap[a->sector_code] = a;
    sectorCodeToNodeMap[b->sector_code] = b;
}

void SpaceSectorBST::displaySectorsInOrder() {
    // TODO: Traverse the space sector BST map in-order and print the sectors 
    // to STDOUT in the given format.
    std::cout << "Space sectors inorder traversal:" << std::endl;
    displayInOrderRecursive(root);
    std::cout << std::endl;
}

void SpaceSectorBST::displaySectorsPreOrder() {
    // TODO: Traverse the space sector BST map in pre-order traversal and print 
    // the sectors to STDOUT in the given format.
    std::cout << "Space sectors preorder traversal:" << std::endl;
    displayPreOrderRecursive(root);
    std::cout << std::endl;
}

void SpaceSectorBST::displaySectorsPostOrder() {
    // TODO: Traverse the space sector BST map in post-order traversal and print 
    // the sectors to STDOUT in the given format.
    std::cout << "Space sectors postorder traversal:" << std::endl;
    displayPostOrderRecursive(root);
    std::cout << std::endl;
}

// Helper functions for recursive traversal
void SpaceSectorBST::displayInOrderRecursive(Sector* node) {
    if (node != nullptr) {
        displayInOrderRecursive(node->left);
        std::cout << node->sector_code << std::endl;
        displayInOrderRecursive(node->right);
    }
}

void SpaceSectorBST::displayPreOrderRecursive(Sector* node) {
    if (node != nullptr) {
        std::cout << node->sector_code << std::endl;
        displayPreOrderRecursive(node->left);
        displayPreOrderRecursive(node->right);
    }
}

void SpaceSectorBST::displayPostOrderRecursive(Sector* node) {
    if (node != nullptr) {
        displayPostOrderRecursive(node->left);
        displayPostOrderRecursive(node->right);
        std::cout << node->sector_code << std::endl;
    }
}

std::vector<Sector*> SpaceSectorBST::getStellarPath(const std::string& sector_code) {
    // TODO: Find the path from the Earth to the destination sector given by its
    // sector_code, and return a vector of pointers to the Sector nodes that are on
    // the path. Make sure that there are no duplicate Sector nodes in the path!
    std::vector<Sector*> path;

    // Check if the sector code exists in the map
    auto targetNodeIter = sectorCodeToNodeMap.find(sector_code);
    if (targetNodeIter == sectorCodeToNodeMap.end()) {
        // Sector code not found
        return path;
    }

    Sector* targetNode = targetNodeIter->second;
    findStellarPathRecursive(root, targetNode->x, targetNode->y, targetNode->z, path);
    return path;
}

void SpaceSectorBST::findStellarPathRecursive(Sector* node, int targetX, int targetY, int targetZ, std::vector<Sector*>& path) {
    if (!node) return;

    path.push_back(node);

    if (node->x == targetX && node->y == targetY && node->z == targetZ) {
        return; // Destination sector found
    }

    if (shouldGoLeft(node, targetX, targetY, targetZ)) {
        findStellarPathRecursive(node->left, targetX, targetY, targetZ, path);
    } else {
        findStellarPathRecursive(node->right, targetX, targetY, targetZ, path);
    }

    if (!isNodeOnPath(node, targetX, targetY, targetZ)) {
        //path.pop_back();
    }
}

bool SpaceSectorBST::isNodeOnPath(Sector* node, int targetX, int targetY, int targetZ) {
    // Check if the current node's coordinates match the target coordinates
    return node->x == targetX && node->y == targetY && node->z == targetZ;
}


void SpaceSectorBST::printStellarPath(const std::vector<Sector*>& path) {
    // TODO: Print the stellar path obtained from the getStellarPath() function 
    // to STDOUT in the given format.
    if (path.empty()){// || path.back()->sector_code != path.front()->sector_code) {
        std::cout << "A path to Dr. Elara could not be found." << std::endl;
    } else {
        std::cout << "The stellar path to Dr. Elara: ";
        for (size_t i = 0; i < path.size(); i++) {
            std::cout << path[i]->sector_code;
            if (i < path.size() - 1) {
                std::cout << "->";
            }
        }
        std::cout << std::endl;
    }
}

bool SpaceSectorBST::shouldGoLeft(Sector* node, int targetX, int targetY, int targetZ) {
    // If the target's X-coordinate is less than the node's, go left.
    if (targetX < node->x) {
        return true;
    }
    // If X-coordinates are equal but the target's Y-coordinate is less, go left.
    else if (targetX == node->x && targetY < node->y) {
        return true;
    }
    // If both X and Y coordinates are equal but the target's Z-coordinate is less, go left.
    else if (targetX == node->x && targetY == node->y && targetZ < node->z) {
        return true;
    }
    // Otherwise, go right.
    return false;
}
