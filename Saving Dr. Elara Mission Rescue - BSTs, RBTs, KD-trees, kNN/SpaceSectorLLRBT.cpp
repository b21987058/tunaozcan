#include "SpaceSectorLLRBT.h"
#include <algorithm>

using namespace std;

SpaceSectorLLRBT::SpaceSectorLLRBT() : root(nullptr) {}

void SpaceSectorLLRBT::readSectorsFromFile(const std::string& filename) {
    // TODO: read the sectors from the input file and insert them into the LLRBT sector map
    // according to the given comparison critera based on the sector coordinates.
    std::ifstream file(filename);
    std::string line;
    int x, y, z;

    // Check if the file opened successfully
    if (!file.is_open()) {
        std::cerr << "Error opening file: " << filename << std::endl;
        return;
    }

    // Skip the header line
    std::getline(file, line);

    while (std::getline(file, line)) {
        std::istringstream iss(line);
        if (iss >> x && iss.ignore() && iss >> y && iss.ignore() && iss >> z) {
            insertSectorByCoordinates(x, y, z);
        }
    }
}

// Remember to handle memory deallocation properly in the destructor.
SpaceSectorLLRBT::~SpaceSectorLLRBT() {
    // TODO: Free any dynamically allocated memory in this class.
    deleteTree(root);
}

void SpaceSectorLLRBT::deleteTree(Sector* node) {
    if (node != nullptr) {
        deleteTree(node->left);
        deleteTree(node->right);
        delete node;
    }
}

void SpaceSectorLLRBT::insertSectorByCoordinates(int x, int y, int z) {
    // TODO: Instantiate and insert a new sector into the space sector LLRBT map 
    // according to the coordinates-based comparison criteria.
    root = insertRecursive(root, x, y, z);
    root->color = BLACK; // The root is always black
}

Sector* SpaceSectorLLRBT::insertRecursive(Sector* node, int x, int y, int z) {
    if (node == nullptr) {
        // Create a new red node
        Sector* new_sector = new Sector(x, y, z, RED);
        sectorMap[new_sector->sector_code] = new_sector;
        return new_sector;
    }

    // Insert based on coordinates
    if (shouldInsertLeft(node, x, y, z)) {
        node->left = insertRecursive(node->left, x, y, z);
        node->left->parent = node;
    } else {
        node->right = insertRecursive(node->right, x, y, z);
        node->right->parent = node;
    }

    // Fix violations
    if (Sector::isRed(node->right) && !Sector::isRed(node->left)) {
        node = rotateLeft(node);
    }

    // Fix two consecutive red links
    if (Sector::isRed(node->left) && Sector::isRed(node->left->left)) {
        node = rotateRight(node);
    }

    // Split 4-nodes
    if (Sector::isRed(node->left) && Sector::isRed(node->right)) {
        flipColors(node);
    }

    return node;
}

bool SpaceSectorLLRBT::shouldInsertLeft(Sector* node, int x, int y, int z) {
    // Primary Criterion: X coordinate
    if (x < node->x) return true;
    if (x > node->x) return false;

    // Secondary Criterion: Y coordinate
    if (y < node->y) return true;
    if (y > node->y) return false;

    // Tertiary Criterion: Z coordinate
    return z < node->z;
}

Sector* SpaceSectorLLRBT::rotateLeft(Sector* node) {
    Sector* temp = node->right;
    node->right = temp->left;

    if (temp->left != nullptr) {
        temp->left->parent = node; // Update parent pointer of the new left child
    }

    temp->parent = node->parent; // Update parent pointer of the rotated node

    if (node->parent == nullptr) { // Node was the root
        root = temp;
    } else if (node == node->parent->left) {
        node->parent->left = temp;
    } else {
        node->parent->right = temp;
    }

    temp->left = node;
    node->parent = temp; // Update the parent pointer of the original node

    temp->color = node->color;
    node->color = RED;

    return temp;
}

Sector* SpaceSectorLLRBT::rotateRight(Sector* node) {
    Sector* temp = node->left;
    node->left = temp->right;

    if (temp->right != nullptr) {
        temp->right->parent = node; // Update parent pointer of the new right child
    }

    temp->parent = node->parent; // Update parent pointer of the rotated node

    if (node->parent == nullptr) { // Node was the root
        root = temp;
    } else if (node == node->parent->right) {
        node->parent->right = temp;
    } else {
        node->parent->left = temp;
    }

    temp->right = node;
    node->parent = temp; // Update the parent pointer of the original node

    temp->color = node->color;
    node->color = RED;

    return temp;
}

void SpaceSectorLLRBT::flipColors(Sector* node) {
    if (node == nullptr || node->left == nullptr || node->right == nullptr) {
        return;
    }
    node->color = !node->color;
    node->left->color = !node->left->color;
    node->right->color = !node->right->color;
}

void SpaceSectorLLRBT::displaySectorsInOrder() {
    std::cout << "Space sectors inorder traversal:" << std::endl;
    displayInOrderRecursive(root);
    std::cout << std::endl;
}

void SpaceSectorLLRBT::displayInOrderRecursive(Sector* node) {
    if (node != nullptr) {
        displayInOrderRecursive(node->left);
        if(node->color)
        {
            std::cout << "RED sector: ";
        }
        else
        {
            std::cout << "BLACK sector: ";
        }
        std::cout << node->sector_code << std::endl;
        displayInOrderRecursive(node->right);
    }
}

void SpaceSectorLLRBT::displaySectorsPreOrder() {
    // TODO: Traverse the space sector LLRBT map in pre-order traversal and print 
    // the sectors to STDOUT in the given format.
    std::cout << "Space sectors preorder traversal:" << std::endl;
    displayPreOrderRecursive(root);
    std::cout << std::endl;
}

void SpaceSectorLLRBT::displayPreOrderRecursive(Sector* node) {
    if (node != nullptr) {
        if(node->color)
        {
            std::cout << "RED sector: ";
        }
        else
        {
            std::cout << "BLACK sector: ";
        }
        std::cout << node->sector_code << std::endl;
        displayPreOrderRecursive(node->left);
        displayPreOrderRecursive(node->right);
    }
}

void SpaceSectorLLRBT::displaySectorsPostOrder() {
    // TODO: Traverse the space sector LLRBT map in post-order traversal and print 
    // the sectors to STDOUT in the given format.
    std::cout << "Space sectors postorder traversal:" << std::endl;
    displayPostOrderRecursive(root);
    std::cout << std::endl;
}

void SpaceSectorLLRBT::displayPostOrderRecursive(Sector* node) {
    if (node != nullptr) {
        displayPostOrderRecursive(node->left);
        displayPostOrderRecursive(node->right);
        if(node->color)
        {
            std::cout << "RED sector: ";
        }
        else
        {
            std::cout << "BLACK sector: ";
        }
        std::cout << node->sector_code << std::endl;
    }
}

std::vector<Sector*> SpaceSectorLLRBT::getStellarPath(const std::string& sector_code) {
    // TODO: Find the path from the Earth to the destination sector given by its
    // sector_code, and return a vector of pointers to the Sector nodes that are on
    // the path. Make sure that there are no duplicate Sector nodes in the path!
    // Find the starting and target nodes
    std::vector<Sector*> startPath, targetPath, combinedPath;

    Sector* startNode = sectorMap["0SSS"];
    if (!startNode) return combinedPath; // Start node not found

    // Find the target node
    Sector* targetNode = sectorMap[sector_code];
    if (!targetNode) return combinedPath; // Target node not found

    while (startNode != nullptr) {
        startPath.push_back(startNode);
        startNode = startNode->parent;
    }

    while (targetNode != nullptr) {
        targetPath.push_back(targetNode);
        targetNode = targetNode->parent;
    }

    // Reverse the paths to make them from root to the nodes
    std::reverse(startPath.begin(), startPath.end());
    std::reverse(targetPath.begin(), targetPath.end());

    // Find the lowest common ancestor
    int lcaIndex = -1;
    for (int i = 0; i < std::min(startPath.size(), targetPath.size()); ++i) {
        if (startPath[i] != targetPath[i]) {
            lcaIndex = i - 1;
            break;
        }
    }

    // If LCA not found it means one of the nodes is root
    if (lcaIndex == -1) {
        lcaIndex = std::min(startPath.size(), targetPath.size()) - 1;
    }

    // Construct the path from the start node to LCA
    for (int i = startPath.size() - 1; i > lcaIndex; --i) {
        combinedPath.push_back(startPath[i]);
    }

    // Construct the path from LCA to the target node
    for (int i = lcaIndex; i < targetPath.size(); ++i) {
        combinedPath.push_back(targetPath[i]);
    }

    return combinedPath;
}

void SpaceSectorLLRBT::printStellarPath(const std::vector<Sector*>& path) {
    // TODO: Print the stellar path obtained from the getStellarPath() function 
    // to STDOUT in the given format.
    if (path.empty()) {
        std::cout << "A path to Dr. Elara could not be found." << std::endl;
    } else {
        std::cout << "The stellar path to Dr. Elara: ";
        for (size_t i = 0; i < path.size(); ++i) {
            std::cout << path[i]->sector_code;
            if (i < path.size() - 1) {
                std::cout << "->";
            }
        }
        std::cout << std::endl;
    }
}
