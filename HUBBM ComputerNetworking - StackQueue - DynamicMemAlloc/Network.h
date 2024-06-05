#ifndef NETWORK_H
#define NETWORK_H

#include <vector>
#include <iostream>
#include "Packet.h"
#include "Client.h"
#include <set>
using namespace std;

class Network {
public:
    Network();
    ~Network();

    // Executes commands given as a vector of strings while utilizing the remaining arguments.
    void process_commands(vector<Client> &clients, vector<string> &commands, int message_limit, const string &sender_port,
                     const string &receiver_port);

    // Initialize the network from the input files.
    vector<Client> read_clients(string const &filename);
    void read_routing_tables(vector<Client> & clients, string const &filename);
    vector<string> read_commands(const string &filename); 
    unordered_map <string, string> mac_address_map;
    set<string> available_clients;
    void print_packet_stack(const stack<Packet*>& originalStack, const int frame_number);
    void print_packet_stack_x(const queue<stack<Packet*>>& client_queue, const int frame_number, const string queue_type, string client_id);
    void print_command(const string command);
    string get_current_time();

    enum layer_id {
        ApplicationLayer,
        TransportLayer,
        NetworkLayer,
        PhysicalLayer
    };
};

#endif  // NETWORK_H
