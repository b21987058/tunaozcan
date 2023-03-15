//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 06.01.2022 17:19:47
// Design Name: 
// Module Name: siganfu_machine_gun
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


`timescale 1ms / 100ns

module siganfu_machine_gun (
	// KARDEÞÝM SEN MAKÝNASIN MAKÝNA <3 GÖKHAN
	input sysclk,
	input reboot,
	input target_locked,
	input is_enemy,
	input fire_command,
	input firing_mode, // 0 single, 1 auto
	input overheat_sensor,
	output reg[2:0] current_state,
	output reg criticality_alert,
	output reg fire_trigger
	
);
    parameter Idle = 3'b000, shoot_single =  3'b001, shoot_auto  =  3'b010, reload  =  3'b011, overheat = 3'b100, downfall = 3'b101;
    //Idle=000, shoot_single =001, shoot_auto= 010, reload= 011, overheat= 100, downfall= 101;// the states that are required for Mealy
    
    reg state, nextstate; //current state and the next state
    /*parameter*/ integer magazine_num = 3, bullet_num = 25;
    reg clk ;
    //toggle clock every 10 time units
    always
    #10 clk =~ clk;
    
always@ (posedge sysclk or posedge reboot) 
begin
if (reboot) // go to state zero if reboot
    state <= Idle;
else
    state <= nextstate;
    current_state =state; //deneysel oldu biraz might delete later
end 

always @(state or target_locked or is_enemy or fire_command or firing_mode or overheat_sensor)
begin
    
    current_state = 3'b000;
    case(state)
    Idle: if(target_locked==1 & is_enemy==1 & fire_command==1 & firing_mode==0)begin//conditions for ýdle -->> shoot single
               nextstate = shoot_single;
               current_state =   3'b001;
               fire_trigger=0;
               if(magazine_num <2)begin
                        criticality_alert=1;
                        end
                    else begin
                        criticality_alert=0;
                    end
          end             
          else if(target_locked==1 & is_enemy==1 & fire_command==1 & firing_mode==1)begin//conditions for ýdle -->> shoot auto
                nextstate = shoot_auto;
                current_state =   3'b010;
                fire_trigger=0;
                if(magazine_num <2)begin
                        criticality_alert=1;
                        end
                    else begin
                        criticality_alert=0;
                    end
          end
          else begin
                nextstate = Idle; //otherwise stay on the IDLE state (if the shooting conds are not satisfied)
                current_state =3'b000;
                fire_trigger=0;
                if(magazine_num <2)begin
                        criticality_alert=1;
                        end
                    else begin
                        criticality_alert=0;
                    end
            end
    shoot_auto: if(overheat_sensor ==1)begin  //if the gun has overheated SIGANFU_MACHINE_GUN should transition to the OVERHEAT state
                    current_state =3'b010;
                    nextstate = overheat;
                    fire_trigger=0;
                    if(magazine_num <2)begin
                        criticality_alert=1;
                        end
                    else begin
                        criticality_alert=0;
                    end
                end
                else if (bullet_num==0) begin //check if there are any bullets left in the currently used magazine==0 yani
                        current_state =3'b010;
                        if( magazine_num==0) begin //there are no magazines left to reload yani
                            nextstate = downfall;//proceed to the DOWNFALL state
                            end
                        else begin
                            nextstate = reload;//proceed to the RELOAD state begin
                            end
               end
               else if(bullet_num !==0  & target_locked==1 & is_enemy==1 & fire_command==1 & firing_mode==1) begin //auto shooting sequence if there are bullets available and the commands are 1
                        current_state =3'b010;
                        while (fire_command ==1 & bullet_num!==0) begin //if there is still fire_command and bullet shoot until either of them becomes 0
                            fire_trigger=1;
                            bullet_num = bullet_num-1;
                            #5;
                            fire_trigger=0;
                            #5;
                            if(overheat_sensor ==1)begin  //if the gun has overheated SIGANFU_MACHINE_GUN should transition to the OVERHEAT state
                                
                                nextstate = overheat;
                                fire_trigger=0;
                            end
                        end
               end
               else begin
                    nextstate = Idle;
                    current_state =3'b010;
                    end
               
      shoot_single: if(overheat_sensor ==1)begin  //if the gun has overheated SIGANFU_MACHINE_GUN should transition to the OVERHEAT state
                    current_state =3'b001;
                    nextstate = overheat;
                    fire_trigger=0;
                    if(magazine_num <2)begin
                        criticality_alert=1;
                        end
                    else begin
                        criticality_alert=0;
                    end
                end
                else if (bullet_num==0) begin //check if there are any bullets left in the currently used magazine==0 yani
                        current_state =3'b001;
                        if( magazine_num==0) begin //there are no magazines left to reload yani
                            nextstate = downfall;//proceed to the DOWNFALL state
                            end
                        else begin
                            nextstate = reload;//proceed to the RELOAD state begin
                            end
                end
                else if(bullet_num !==0  & target_locked==1 & is_enemy==1 & fire_command==1 & firing_mode==0) begin //single shooting sequence if there are bullets available and conditions are satisfied
                        current_state =3'b010;
                        if(fire_command !==0)begin
                            bullet_num = bullet_num -1;//a single shot has been fired
                            fire_trigger=1; //bundan hiç emin deðilim bu kadar kolay olamaz bu kýsým
                            if(overheat_sensor ==1)begin  //if the gun has overheated SIGANFU_MACHINE_GUN should transition to the OVERHEAT state
                                    
                                    nextstate = overheat;
                                    fire_trigger=0;
                            end
                        end
                        else begin
                            nextstate = Idle;
                            current_state =3'b010;
                        end
               end
               
      reload: if (magazine_num !==0)begin //if there are available magazines bullet number becomes 25
                    current_state=3'b011;
                    #50; //bunun yerinden hiç emin deðilim
                    magazine_num = magazine_num-1;
                    bullet_num=25;
                    if(magazine_num==0)begin
                        criticality_alert=1;
                    end
                    else begin
                        criticality_alert=0;
                    end
                    if(bullet_num !==0  & target_locked==1 & is_enemy==1 & fire_command==1 & firing_mode==0)begin //proceed to single shooting mode
                        nextstate = shoot_single;
                    end
                    else if (bullet_num !==0  & target_locked==1 & is_enemy==1 & fire_command==1 & firing_mode==1)begin //proceed to auto shooting mode
                        nextstate= shoot_auto;
                    end
              end
      overheat: if(overheat_sensor==1)begin //must wait for 100 milliseconds for the cooling process to complete
                    #100;
                    if(target_locked==1 & is_enemy==1 & fire_command==1 & firing_mode==1)begin//conditions for ýdle -->> shoot auto
                        if(bullet_num==0)begin
                          if(magazine_num==0)begin
                               nextstate=downfall;
                          end
                          else if(magazine_num!==0)begin
                                nextstate=reload;
                          end
                     end
                     else begin
                        nextstate = shoot_auto;
                        current_state =   3'b010;
                        if(magazine_num <2)begin
                           criticality_alert=1;
                        end
                        else begin
                           criticality_alert=0;
                        end
                     end   
                 end
                    else if(target_locked==1 & is_enemy==1 & fire_command==1 & firing_mode==0)begin//conditions for ýdle -->> shoot single
                       if(bullet_num==0)begin
                          if(magazine_num==0)begin
                               nextstate=downfall;
                          end
                          else if(magazine_num!==0)begin
                                nextstate=reload;
                          end
                       end
                       else begin
                           nextstate = shoot_single;
                           current_state =   3'b001;
                           if(magazine_num <2)begin
                               criticality_alert=1;
                           end
                           else begin
                               criticality_alert=0;
                           end
                       end
                    end
                    else begin
                    nextstate=Idle;
                    end             
                end
            
        downfall:  if(bullet_num == 0 & magazine_num == 0)begin //the machine gun enters this state when 
                        while(reboot==0)begin                   //it runs out of bullets and magazines
                            #1;
                            continue;
                            
                        end
                        if(reboot==1)begin
                            nextstate=Idle;
                        end
                   end
        
        default: 
                nextstate=Idle;
                
                    
                    
               
               
               
               
               
               
               
               
               
               
               
               
               
               endcase
               end
endmodule
