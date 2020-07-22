import re

final_coords_files=["data1.txt","data2.txt","data3.txt"]

init_coords_files = ["20160621111259468#1.kpos", "20160621112413861#1.kpos", "20160621112927113#1.kpos"]

def main():

    for i in range(0,3):
        with open(init_coords_files[i], 'r') as f:
            with open(final_coords_files[i], "w") as fout:
                all_lines = f.readlines()
                #Discard first two lines of coords file
                all_lines = all_lines[2:]
                for l in all_lines:
                    #find pattern (color buffer and depth buffer)
                    tmp =  re.findall("\[\d+\.\d+;\d+\.\d+\];\[\d+\.\d+;\d+\.\d+\]", l)
                    #if at least one pattern found 
                    if len(tmp) != 0:
                        #output format of pattern will be coordx0,coordy0;coordx1,coordy1;...;coordxn,coordyn;
                        for coord_group in tmp:
                            coord_group = coord_group.split("];[")
                            coord_group = coord_group[1].strip("]")
                            coord_group = coord_group.replace(";", ",")
                            fout.write(coord_group)
                            fout.write(";")                    
                        fout.write("\n")
                fout.close()
            f.close()
            

        

main()