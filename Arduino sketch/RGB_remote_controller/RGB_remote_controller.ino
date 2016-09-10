#include <SPI.h>
#include <Ethernet.h>
#include <EthernetUdp.h>

const int red_pin = 9;
const int green_pin = 5;
const int blue_pin = 6;

byte mac[] = {0x90, 0xA2, 0xDA, 0x0F, 0xC4, 0x58};
IPAddress ip(192, 168, 1, 58);
EthernetUDP udp_socket;
int port = 7878;

char packet_data[UDP_TX_PACKET_MAX_SIZE];
int red;
int green;
int blue;
int commas[2];

void setup()
{
  Serial.begin(9600);
  
  Ethernet.begin(mac, ip);
  udp_socket.begin(port);

  pinMode(red_pin, OUTPUT);
  pinMode(green_pin, OUTPUT);
  pinMode(blue_pin, OUTPUT);

  analogWrite(red_pin, 0);
  analogWrite(red_pin, 0);
  analogWrite(red_pin, 0);
}

void loop()
{
  int packet_size = udp_socket.parsePacket();

  if (packet_size)
  {
    udp_socket.read(packet_data, UDP_TX_PACKET_MAX_SIZE);
    String data_to_str = packet_data;
    int k = 0;

    for (int i = 0; i < UDP_TX_PACKET_MAX_SIZE; i++)
    {
      if (packet_data[i] == ',')
      {
        commas[k] = i;
        k++;
      }
    }
    
    red = data_to_str.substring(1, data_to_str.indexOf(commas[0])).toInt();
    green = data_to_str.substring(commas[0] + 1, data_to_str.indexOf(commas[1])).toInt();
    blue = data_to_str.substring(commas[1] + 1).toInt();

    analogWrite(red_pin, red);
    analogWrite(green_pin, green);
    analogWrite(blue_pin, blue);
  }
  delay(1);
}
