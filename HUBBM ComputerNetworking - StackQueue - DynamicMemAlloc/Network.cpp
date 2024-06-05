#include "Network.h"

#include <iomanip>
#include <sstream>
#include <chrono>


Network::Network() {

}

void Network::process_commands(vector<Client> &clients, vector<string> &commands, int message_limit,
                      const string &sender_port, const string &receiver_port) {
    // TODO: Execute the commands given as a vector of strings while utilizing the remaining arguments.
    /* Don't use any static variables, assume this method will be called over and over during testing.
     Don't forget to update the necessary member variables after processing each command. For example,
     after the MESSAGE command, the outgoing queue of the sender must have the expected frames ready to send. */

    for (const string& command : commands) {
        stringstream ss(command);
        string commandType;
        ss >> commandType;
        print_command(command);
        if (commandType == "MESSAGE") {
            string sender_Id, receiver_Id, messageWithHashes, message;
            // Extract senderId, receiverId
            ss >> sender_Id >> receiver_Id;
            // Extract the rest of the string including hashes
            getline(ss, messageWithHashes);

            // Find the position of the first and second # character
            size_t start = messageWithHashes.find('#');
            size_t end = messageWithHashes.rfind('#');

            if (start != string::npos && end != string::npos && start < end) {
                // Extract the message between the two # characters
                message = messageWithHashes.substr(start + 1, end - start - 1);
            }

            // Find the sender client
            auto sender_it = find_if(clients.begin(), clients.end(), [&](const Client& client) {
                return client.client_id == sender_Id;
            });
            // Find the receiver client
            auto receiver_it = find_if(clients.begin(), clients.end(), [&](const Client& client) {
                return client.client_id == receiver_Id;
            });

            if (sender_it != clients.end() && receiver_it != clients.end()) {
                // Fragment the message and create packets
                // Each packet is wrapped in a stack and queued in the sender's outgoing queue
                // This involves creating ApplicationLayerPacket, TransportLayerPacket, etc.
                // and pushing them onto a stack

                cout << "Message to be sent: \"" << message << "\"" << endl << endl;

                // Calculate the number of packets needed
                int num_packets = (message.length() + message_limit - 1) / message_limit;

                for (int i = 0; i < num_packets; ++i) {
                    // Extract a part of the message of size message_limit
                    string _message_data = message.substr(i * message_limit, message_limit);
                    stack<Packet*> frames;

                    Packet* app_packet = new ApplicationLayerPacket(layer_id::ApplicationLayer, sender_Id, receiver_Id, _message_data);
                    Packet* transport_packet = new TransportLayerPacket(layer_id::TransportLayer, sender_port, receiver_port);
                    Packet* network_packet = new NetworkLayerPacket(layer_id::NetworkLayer, sender_it->client_ip, receiver_it->client_ip);
                    Packet* phy_packet = new PhysicalLayerPacket(layer_id::PhysicalLayer, sender_it->client_mac, mac_address_map[sender_it->routing_table[receiver_Id]]);

                    frames.push(app_packet);
                    frames.push(transport_packet);
                    frames.push(network_packet);
                    frames.push(phy_packet);

                    sender_it->outgoing_queue.push(frames);
                    cout << "Frame #" << i+1 << endl;
                    print_packet_stack(frames, i+1);
                }

            }
        }else if (commandType == "SHOW_FRAME_INFO") {
            string client_id, q_type, str_stack_number;
            ss >> client_id >> q_type >> str_stack_number;
            int stack_number = stoi(str_stack_number);

            auto client_it = find_if(clients.begin(), clients.end(), [&](const Client& client) {
                return client.client_id == client_id;
            });
            if(client_it != clients.end())
            {
                if(q_type =="out")
                {
                    if(client_it != clients.end())
                    {
                        print_packet_stack_x(client_it->outgoing_queue,  stack_number, "outgoing", client_id);
                    }
                }
                if(q_type =="in")
                {
                    if(client_it != clients.end())
                    {
                        print_packet_stack_x(client_it->incoming_queue,  stack_number, "incoming", client_id);
                    }
                }
            }
        }else if (commandType == "SHOW_Q_INFO") {
            string client_id, q_type;
            ss >> client_id >> q_type;
            auto client_it = find_if(clients.begin(), clients.end(), [&](const Client& client) {
                return client.client_id == client_id;
            });
            if(client_it != clients.end())
            {
                if(q_type =="out")
                {
                    if(client_it != clients.end())
                    {
                        cout << "Client " << client_id << " Outgoing Queue Status" << endl;
                        cout << "Current total number of frames: " << client_it->outgoing_queue.size() << endl;
                    }
                }
                if(q_type =="in")
                {
                    if(client_it != clients.end())
                    {
                        cout << "Client " << client_id << " Incoming Queue Status" << endl;
                        cout << "Current total number of frames: " << client_it->incoming_queue.size() << endl;
                    }
                }
            }

        } else if (commandType == "SEND") {
            // Handle SEND command
            // Move frames from outgoing queues to the appropriate incoming queues
            // Iterate through each client
            string message_data = "";
            int message_frame_number = 0;
            for (auto& client : clients) {
                while (!client.outgoing_queue.empty()) {
                    message_frame_number++;
                    // Get the top frame from the outgoing queue
                    stack<Packet*> frame = client.outgoing_queue.front();
                    client.outgoing_queue.pop();
                    stack<Packet*> temp_stack = frame;

                    auto* phy_packet = dynamic_cast<PhysicalLayerPacket*>(temp_stack.top());
                    temp_stack.pop();
                    auto* net_packet = dynamic_cast<NetworkLayerPacket*>(temp_stack.top());
                    temp_stack.pop();
                    auto* trans_packet = dynamic_cast<TransportLayerPacket*>(temp_stack.top());
                    temp_stack.pop();
                    auto* app_packet = dynamic_cast<ApplicationLayerPacket*>(temp_stack.top());
                    temp_stack.pop();

                    // Find the client that matches the next hop MAC address
                    auto nextHopClientIt = find_if(clients.begin(), clients.end(), [&](const Client& client) {
                        return client.client_mac == phy_packet->receiver_MAC_address;
                    });

                    nextHopClientIt->incoming_queue.push(frame);

                    message_data += app_packet->message_data;

                    // Print out
                    cout << "Client " << client.client_id << " sending frame #" << message_frame_number << " to client " << nextHopClientIt->client_id << endl;

                    // Logging the send action for the sender client. End of message
                    if (message_data.find('!') != std::string::npos || message_data.find('.') != std::string::npos || message_data.find('?') != std::string::npos)
                    {
                        if(app_packet->sender_ID == client.client_id)
                        {
                            client.log_entries.emplace_back(get_current_time(), message_data, message_frame_number, phy_packet->hop_count, app_packet->sender_ID, app_packet->receiver_ID, true, ActivityType::MESSAGE_SENT);
                        }
                        message_frame_number = 0;
                        message_data = "";
                    }
                    frame.top()->hop_count++;
                    print_packet_stack(frame, message_frame_number);
                }
            }
        } else if (commandType == "RECEIVE") {
            // Handle RECEIVE command
            // Process frames in each client's incoming queue
            // Iterate through each client
            int frame_number = 0;
            string complete_message = "";
            for (auto& client : clients) {
                while (!client.incoming_queue.empty()) {
                    frame_number++;
                    // Get the top frame from the incoming queue
                    stack<Packet*> frame = client.incoming_queue.front();
                    client.incoming_queue.pop();

                    stack<Packet*> temp_stack = frame;

                    auto* phy_packet = dynamic_cast<PhysicalLayerPacket*>(temp_stack.top());
                    temp_stack.pop();
                    auto* net_packet = dynamic_cast<NetworkLayerPacket*>(temp_stack.top());
                    temp_stack.pop();
                    auto* trans_packet = dynamic_cast<TransportLayerPacket*>(temp_stack.top());
                    temp_stack.pop();
                    auto* app_packet = dynamic_cast<ApplicationLayerPacket*>(temp_stack.top());
                    temp_stack.pop();

                    complete_message += app_packet->message_data;

                    // Find the physical sender
                    auto physical_sender = find_if(clients.begin(), clients.end(), [&](const Client& client) {
                        return client.client_mac == phy_packet->sender_MAC_address;
                        });

                    // Arrived at final destination
                    if(app_packet->receiver_ID == client.client_id)
                    {
                        cout << "Client " << client.client_id << " receiving frame #" << frame_number << " from client " << physical_sender->client_id << ", originating from client " << app_packet->sender_ID << endl;
                        print_packet_stack(frame, frame_number);
                        if (complete_message.find('!') != std::string::npos || complete_message.find('.') != std::string::npos || complete_message.find('?') != std::string::npos)
                        {
                            client.log_entries.emplace_back(get_current_time(), complete_message, frame_number, phy_packet->hop_count, app_packet->sender_ID, app_packet->receiver_ID, true, ActivityType::MESSAGE_RECEIVED);
                            cout << "Client " << client.client_id << " received the message \"" << complete_message << "\" from client " << app_packet->sender_ID << "." << endl;
                            frame_number = 0;
                            complete_message = "";
                            cout << "--------" << endl;
                        }
                        // Delete packets
                        delete phy_packet;
                        delete net_packet;
                        delete trans_packet;
                        delete app_packet;

                    }
                    // Not at final destination, needs to be forwarded
                    else
                    {
                        // Change the mac address
                        auto nextHopClientIt = find_if(clients.begin(), clients.end(), [&](const Client& client) {
                            return client.client_mac == phy_packet->receiver_MAC_address;
                            });

                        if (!available_clients.count(client.routing_table[app_packet->receiver_ID]))
                        {
                            cout << "Client " << client.client_id << " receiving frame #" << frame_number << " from client " << physical_sender->client_id << ", but intended for client " << app_packet->receiver_ID << ". Forwarding... " << endl;
                            cout << "Error: Unreachable destination. Packets are dropped after " << phy_packet->hop_count << " hops!" << endl;
                            if (complete_message.find('!') != std::string::npos || complete_message.find('.') != std::string::npos || complete_message.find('?') != std::string::npos)
                            {
                                client.log_entries.emplace_back(get_current_time(), complete_message, frame_number, phy_packet->hop_count, app_packet->sender_ID, app_packet->receiver_ID, false, ActivityType::MESSAGE_DROPPED);
                                frame_number = 0;
                                complete_message = "";
                                cout << "--------" << endl;
                            }
                            delete phy_packet;
                            delete net_packet;
                            delete trans_packet;
                            delete app_packet;
                        }
                        else
                        {
                            if(frame_number == 1)
                            {
                                cout << "Client " << client.client_id << " receiving a message from client " << physical_sender->client_id << ", but intended for client " << app_packet->receiver_ID << ". Forwarding... " << endl;
                            }
                            phy_packet->sender_MAC_address = client.client_mac;
                            phy_packet->receiver_MAC_address = mac_address_map[client.routing_table[app_packet->receiver_ID]];
                            cout << "Frame #" << frame_number << " MAC address change: New sender MAC " << phy_packet->sender_MAC_address << ", new receiver MAC " << phy_packet->receiver_MAC_address << endl;
                            frame.pop();
                            frame.push(phy_packet);
                            // Put into outgoing queue
                            client.outgoing_queue.push(frame);
                            if (complete_message.find('!') != std::string::npos || complete_message.find('.') != std::string::npos || complete_message.find('?') != std::string::npos)
                            {
                                client.log_entries.emplace_back(get_current_time(), complete_message, frame_number, phy_packet->hop_count, app_packet->sender_ID, app_packet->receiver_ID, true, ActivityType::MESSAGE_FORWARDED);
                                frame_number = 0;
                                complete_message = "";
                                cout << "--------" << endl;
                            }
                        }
                    }


                }
            }
        } else if (commandType == "PRINT_LOG") {
            // Handle PRINT_LOG command
            // Display log details for a specified client
            string clientId;
            ss >> clientId;

            // Find the client
            auto clientIt = find_if(clients.begin(), clients.end(), [&](const Client& client) {
                return client.client_id == clientId;
            });
            if (clientIt != clients.end()) {
                clientIt->print_log();
            }
        }
        else {
            cout << "Invalid command." << endl;
        }
    }
}

vector<Client> Network::read_clients(const string &filename) {
    // TODO: Read clients from the given input file and return a vector of Client instances.
    vector<Client> clients;

    ifstream file(filename);
    if (!file.is_open()) {
        cerr << "Error opening file: " << filename << endl;
        return clients;
    }

    string line;
    int numClients;
    if (getline(file, line)) {
        stringstream ss(line);
        ss >> numClients; // Read the number of clients
    }

    for(int i = 0; i < numClients; i++){
        getline(file, line);
        stringstream ss(line);
        string id, ip, mac;
        ss >> id >> ip >> mac;
        available_clients.insert(id);
        clients.emplace_back(id, ip, mac); // Construct Client and add to the vector
        mac_address_map[id] = mac;
    }

    file.close();
    return clients;
}

void Network::read_routing_tables(vector<Client> &clients, const string &filename) {
    ifstream file(filename);
    if (!file.is_open()) {
        cerr << "Error opening file: " << filename << endl;
        return;
    }

    string line;
    size_t clientIndex = 0;

    while (getline(file, line)) {
        if (line == "-") {
            // Move to the next client when a dash is encountered
            clientIndex++;
            if (clientIndex >= clients.size()) {
                break; // Break if there are no more clients
            }
            continue;
        }

        stringstream ss(line);
        string destination, nextHop;
        ss >> destination >> nextHop;

        // Ensure the client exists in the clients vector
        if (clientIndex < clients.size()) {
            clients[clientIndex].routing_table[destination] = nextHop;
        }
    }

    file.close();
}

// Returns a list of token lists for each command
vector<string> Network::read_commands(const string &filename) {
    vector<string> commands;

    ifstream file(filename);
    if (!file.is_open()) {
        cerr << "Error opening file: " << filename << endl;
        return commands;
    }

    string line;
    int num_commands;
    if (getline(file, line)) {
        stringstream ss(line);
        ss >> num_commands; // Read the number of clients
    }


    while (getline(file, line)) {
        commands.push_back(line);
    }

    file.close();
    return commands;
}

void Network::print_packet_stack(const stack<Packet*>& print_stack, const int frame_number)
{
    // Copy the original stack to preserve its order
    stack<Packet*> tempStack = print_stack;



    // Print details for each packet
    //cout << "Frame #" << frame_number << endl;

    auto* phy_packet = dynamic_cast<PhysicalLayerPacket*>(tempStack.top());
    tempStack.pop();
    phy_packet->print();

    auto* net_packet = dynamic_cast<NetworkLayerPacket*>(tempStack.top());
    tempStack.pop();
    net_packet->print();

    auto* trans_packet = dynamic_cast<TransportLayerPacket*>(tempStack.top());
    tempStack.pop();
    trans_packet->print();

    auto* app_packet = dynamic_cast<ApplicationLayerPacket*>(tempStack.top());
    tempStack.pop();
    app_packet->print();

    cout << "Message chunk carried: \"" << app_packet->message_data << "\"" << endl;
    cout << "Number of hops so far: " << phy_packet->hop_count << endl;
    cout << "--------" << endl;

}

void Network::print_packet_stack_x(const queue<stack<Packet*>>& client_queue, const int frame_number, const string queue_type, string client_id)
{
    if (frame_number <= 0 || frame_number > client_queue.size()) {
        cout << "No such frame." << endl;
        return;
    }

    queue<stack<Packet*>> tempQueue = client_queue;  // Copy the queue to preserve the original

    // Pop to the x-th frame in the queue
    for (int i = 1; i < frame_number; ++i) {
        tempQueue.pop();
    }

    // Now tempQueue.front() is the x-th frame
    const stack<Packet*>& currentStack = tempQueue.front();

    // Copy the stack to pop out elements
    stack<Packet*> tempStack = currentStack;

    // Pop all layers from the copied stack
    auto* phy_packet = dynamic_cast<PhysicalLayerPacket*>(tempStack.top());
    tempStack.pop();
    auto* net_packet = dynamic_cast<NetworkLayerPacket*>(tempStack.top());
    tempStack.pop();
    auto* trans_packet = dynamic_cast<TransportLayerPacket*>(tempStack.top());
    tempStack.pop();
    auto* app_packet = dynamic_cast<ApplicationLayerPacket*>(tempStack.top());
    tempStack.pop();

    cout << "Current Frame #" << frame_number << " on the " << queue_type << " queue of client " << client_id << endl;
    cout << "Carried Message: \"" << app_packet->message_data << "\"" << endl;
    cout << "Layer 0 info: ";
    app_packet->print();
    cout << "Layer 1 info: ";
    trans_packet->print();
    cout << "Layer 2 info: ";
    net_packet->print();
    cout << "Layer 3 info: ";
    phy_packet->print();
    cout << "Number of hops so far: " << phy_packet->hop_count << endl;

}

void Network::print_command(const string command)
{
    for(int i = 0; i < command.size() + 9; i++)
    {
        cout << "-";
    }
    cout << endl;

    cout << "Command: " << command << endl;

    for(int i = 0; i < command.size() + 9; i++)
    {
        cout << "-";
    }
    cout << endl;
}
string Network::get_current_time()
{
    // Get current time as time_point
    auto now = std::chrono::system_clock::now();

    // Convert to time_t for conversion to tm struct
    std::time_t now_time_t = std::chrono::system_clock::to_time_t(now);

    // Convert to tm struct
    struct tm *now_tm;
    now_tm = localtime(&now_time_t);

    // Create a stringstream to format the date and time
    std::stringstream ss;
    ss << std::put_time(now_tm, "%Y-%m-%d %H:%M:%S");
    return ss.str();
}
Network::~Network() {
    // TODO: Free any dynamically allocated memory if necessary.
}