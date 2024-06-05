//
// Created by alperen on 27.09.2023.
//

#include "Client.h"

Client::Client(string const& _id, string const& _ip, string const& _mac) {
    client_id = _id;
    client_ip = _ip;
    client_mac = _mac;
}

ostream &operator<<(ostream &os, const Client &client) {
    os << "client_id: " << client.client_id << " client_ip: " << client.client_ip << " client_mac: "
       << client.client_mac << endl;
    return os;
}

void Client::print_log()
{
    if(log_entries.size() == 0)
    {
        return;
    }
    cout << "Client " << client_id <<" Logs:" << endl;

    for(int i = 0; i < log_entries.size(); i++)
    {
        cout << "--------------" << endl;
        cout << "Log Entry #" << i+1 << ":" << endl;
        log_entries[i].print();
    }
}

Client::~Client() {
    // TODO: Free any dynamically allocated memory if necessary.
    // Free the dynamically allocated Packet objects in the incoming_queue
    while (!incoming_queue.empty()) {
        stack<Packet*>& packets = incoming_queue.front();
        while (!packets.empty()) {
            delete packets.top(); // Delete the dynamically allocated Packet
            packets.pop();        // Remove the null pointer from the stack
        }
        incoming_queue.pop();     // Remove the stack from the queue
    }


    // Repeat the same process for the outgoing_queue
    while (!outgoing_queue.empty()) {
        stack<Packet*>& packets = outgoing_queue.front();
        while (!packets.empty()) {
            delete packets.top();
            packets.pop();
        }
        outgoing_queue.pop();
    }
}
