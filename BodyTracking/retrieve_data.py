import time
import json
from kafka import KafkaProducer
from kafka.errors import KafkaError
from datetime import datetime




def main():
    #all 3 data files
    files = ["data1.txt", "data2.txt", "data3.txt"]
    print("What is you username? ")
    username = input()
    #connect to kafka server and transforms data into json
    #producer = KafkaProducer(bootstrap_servers=['192.168.160.103:9092'],value_serializer=lambda x: json.dumps(x).encode('utf-8'))
    producer = KafkaProducer(bootstrap_servers=['192.168.160.103:9093'],value_serializer=lambda x: json.dumps(x).encode('utf-8'))

    #for each file reads all lines
    for f in files:
        with open(f, "r") as fin:
            time.sleep(2)
            #send first line to producer

            line = fin.readline()

            if line != "":

                print(line)
                line = line.strip()
                # datetime object containing current date and time
                now = datetime.now()
                # dd/mm/YY H:M:S
                date_reading = now.strftime("%d/%m/%Y - %H:%M:%S.%f")

                data = {"username":username, "date_reading": date_reading, "joints": line}
                producer.send('esp21_joints', value=data)
                time.sleep(0.5)
                #while there is a line, reads line and send it to producer topic joints, must wait 0.1 seconds between each reading to simulate body movement
                while line:
                    line = fin.readline()
                    if line != "":

                        print(line)
                        line = line.strip()
                        # datetime object containing current date and time
                        now = datetime.now()
                        # dd/mm/YY H:M:S
                        date_reading = now.strftime("%d/%m/%Y - %H:%M:%S.%f")
                        data = {"username":username, "date_reading": date_reading, "joints": line}

                        producer.send('esp21_joints', value=data)
                        time.sleep(1)



main()
