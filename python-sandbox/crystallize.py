#!/usr/bin/env python3

import numpy as np
import random
import math
import sys
from PIL import Image

def crystallize(img, cnt):
    # Make output image same size
    res = np.zeros_like(img)
    h, w = img.shape[:2]

    # Generate some randomly placed crystal centres
    nx = np.random.randint(0,w,cnt,dtype=np.uint16)
    ny = np.random.randint(0,h,cnt,dtype=np.uint16)

    # Pick colors for crystals
    sRGB = []
    miss = 0
    print ("\n\n>>>\nIMG[NY,NX]\n>>>\n\n")
    for i in range(cnt):
        print (img[ny[i],nx[i]].shape[:2])
        if np.all([img[ny[i],nx[i]][0:2], [0,0,0]]):
            miss += 1
        #else:
        # use colours at those locations from source image
        #sRGB.append(img[ny[i],nx[i]])
        # or randomize colors
        sRGB.append(np.append(np.random.randint(0,255,3), [255]))
    #print ("\n\n>>>>\nSRGB\n>>>\n\n")
    #print (sRGB)
    print ("\n\n>>>>\nMISS\n>>>\n\n")
    print (miss)

    # Iterate over image
    for y in range(h):
        for x in range(w):
            # Find nearest crystal centre...
            dmin = sys.float_info.max
            for i in range(cnt):
                d = (y-ny[i])*(y-ny[i]) + (x-nx[i])*(x-nx[i])
                if d < dmin:
                    dmin = d
                    j = i
            # ... and copy a color to result
            res[y,x,:] = sRGB[j]
    return res

# Open image, crystallize and save
if len(sys.argv) == 1:
    print ("\nCrystallize an image.\n\nUsage:\n$> python3 crystallize.py <source file> <crystal count>\n")
else:
    source = sys.argv[1]
    count = int(sys.argv[2])
    dest = str.join("output.", source)
    print ("\nSource: ",  source, "\nDestination: ", dest, "\nCrystal Count: ", count, "\n\nCrystallizing image...\n\n", sep="", end="")
    img  = Image.open(source)
    res = crystallize(np.array(img),count)
    Image.fromarray(res).save(dest)
