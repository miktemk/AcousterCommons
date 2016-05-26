//
// Translated by CS2J (http://www.cs2j.com): 07/03/2014 11:40:41 AM
//

package utils.mpeg;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

//import CS2JNet.JavaSupport.language.RefSupport;

/** 
Summary description for Class1.
*/
public class MPEGFunctions
{
	// Class of functions and variables needed to encode images
	// using the MPEG1 format
	// The MemoryStream ms is provided to store the bitstream 
	// produced by encoding the image's blocks
	private ByteArrayOutputStream  ms = new ByteArrayOutputStream();
	private static float[][] cosine = new float[][] {
		{1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,},
		{0.98078525f,0.8314696f,0.55557024f,0.19509032f,-0.19509032f,-0.55557024f,-0.8314696f,-0.98078525f,},
		{0.9238795f,0.38268343f,-0.38268343f,-0.9238795f,-0.9238795f,-0.38268343f,0.38268343f,0.9238795f,},
		{0.8314696f,-0.19509032f,-0.98078525f,-0.55557024f,0.55557024f,0.98078525f,0.19509032f,-0.8314696f,},
		{0.70710677f,-0.70710677f,-0.70710677f,0.70710677f,0.70710677f,-0.70710677f,-0.70710677f,0.70710677f,},
		{0.55557024f,-0.98078525f,0.19509032f,0.8314696f,-0.8314696f,-0.19509032f,0.98078525f,-0.55557024f,},
		{0.38268343f,-0.9238795f,0.9238795f,-0.38268343f,-0.38268343f,0.9238795f,-0.9238795f,0.38268343f,},
		{0.19509032f,-0.55557024f,0.8314696f,-0.98078525f,0.98078525f,-0.8314696f,0.55557024f,-0.19509032f,},
	};
	/*{
		{1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,},
		{0.9807852804032304,0.8314696123025452,0.5555702330196023,0.19509032201612833,-0.1950903220161282,-0.555570233019602,-0.8314696123025453,-0.9807852804032304,},
		{0.9238795325112867,0.38268343236508984,-0.3826834323650897,-0.9238795325112867,-0.9238795325112868,-0.38268343236509034,0.38268343236509,0.9238795325112865,},
		{0.8314696123025452,-0.1950903220161282,-0.9807852804032304,-0.5555702330196022,0.5555702330196018,0.9807852804032304,0.19509032201612878,-0.8314696123025451,},
		{0.7071067811865476,-0.7071067811865475,-0.7071067811865477,0.7071067811865474,0.7071067811865477,-0.7071067811865467,-0.7071067811865471,0.7071067811865466,},
		{0.5555702330196023,-0.9807852804032304,0.1950903220161283,0.8314696123025456,-0.8314696123025451,-0.19509032201612803,0.9807852804032307,-0.5555702330196015,},
		{0.38268343236508984,-0.9238795325112868,0.9238795325112865,-0.3826834323650899,-0.38268343236509056,0.9238795325112867,-0.9238795325112864,0.38268343236508956,},
		{0.19509032201612833,-0.5555702330196022,0.8314696123025456,-0.9807852804032307,0.9807852804032304,-0.831469612302545,0.5555702330196015,-0.19509032201612858,},
	};//*/
	private double SQRT2o2 = Math.sqrt(2.0) / 2.0;

	// MPEG1 default quantization matrix
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private short[,] defaultQ = new short[8,8] {{8,16,19,22,26,27,29,34}, {16,16,22,24,27,29,34,37}, {19,22,26,27,29,34,34,38}, {22,22,26,27,29,34,37,40}, {22,26,27,29,32,35,40,48}, {26,27,29,32,35,40,48,58}, {26,27,29,34,38,46,56,69}, {27,29,35,38,46,56,69,83}};
	private short[][] defaultQ = new short[][] {{8,16,19,22,26,27,29,34}, {16,16,22,24,27,29,34,37}, {19,22,26,27,29,34,34,38}, {22,22,26,27,29,34,37,40}, {22,26,27,29,32,35,40,48}, {26,27,29,32,35,40,48,58}, {26,27,29,34,38,46,56,69}, {27,29,35,38,46,56,69,83}};

	// MPEG1 default DC Huffman codes 
	public int[] DCLumCode = new int[] {4, 0, 1, 5, 6, 14, 30, 62, 126};
	public int[] DCLumSize = new int[] {3, 2, 2, 3, 3, 4, 5, 6, 7};
	public int[] DCChromCode = new int[] {0, 1, 2, 6, 14, 30, 62, 126, 254};
	public int[] DCChromSize = new int[] {2, 2, 2, 3, 4, 5, 6, 7, 8};

	// MPEG1 default AC Huffman codes
	private int[] ACcode = new int[] {6,6,10,14,12,14,10,8,14,10,78,70,68,64,28,26,16, 62,52,50,46,44,62,60,58,56,54,62,60,58,56,54, 8,12,8,72,30,18,60,42,34,34,32,52,50,48,46,44,42, 10,74,22,56,36,36,40, 12,24,40,38, 76,54,40, 66,44, 20,42, 58,62, 48,60, 38,58, 32,56, 52,54, 50,52, 48,50, 46,38, 62,36, 62,34, 58,32, 56,54,52,50,48,46,44,42,40,38,36,34,32, 48,46,44,42,40,38,36,34,32};

	private int[] ACsize = new int[] {3,4,5,6,6,7,7,7,8,8,9,9,9,9,11,11,11, 13,13,13,13,13,14,14,14,14,14,17,17,17,17,17, 5,7,8,9,11,11,13,13,13,14,14,17,17,17,17,17,17, 6,9,11,13,13,14,17, 8,11,13,14, 9,13,14, 9,14, 11,14, 13,16, 13,16, 13,16, 13,16, 14,16, 14,16, 14,16, 14,17, 15,17, 15,17, 15,17, 15,15,15,15,15,15,15,15,15,15,15,15,15, 16,16,16,16,16,16,16,16,16};

	// MPEG1 sequence header
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public short[] seqHeaderBits = new short[100] { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,1,1,0,1,1,0,0,1,1, 0,0,0,1,0,1,1,0,0,0,0,0, 0,0,0,0,1,1,1,1,0,0,0,0, 1,1,0,0,0,1,0,0, 0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0, 1, 0,0,0,0,0,1,0,1,0,0, 0,0,0,255,255,255,255};
	public short[] seqHeaderBits = new short[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,1,1,0,1,1,0,0,1,1, 0,0,0,1,0,1,1,0,0,0,0,0, 0,0,0,0,1,1,1,1,0,0,0,0, 1,1,0,0,0,1,0,0, 0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0, 1, 0,0,0,0,0,1,0,1,0,0, 0,0,0,255,255,255,255};

	// MPEG1 GOP header
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public short[] GOPHeaderBits = new short[68] { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,1,1,0,1,1,1,0,0,0, 1,0,0,0,0,0,0,0,0,0,0,0, 1,0,0,0,0,0,0,0,0,0,0,0,0, 1,0,0,0,0,0,0,255,255,255,255};
	public short[] GOPHeaderBits = new short[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,1,1,0,1,1,1,0,0,0, 1,0,0,0,0,0,0,0,0,0,0,0, 1,0,0,0,0,0,0,0,0,0,0,0,0, 1,0,0,0,0,0,0,255,255,255,255};

	// MPEG1 picture header
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public short[] picHeaderBits = new short[68] { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0, 0,0,1, 1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,255,255,255,255};
	public short[] picHeaderBits = new short[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0, 0,0,1, 1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,255,255,255,255};

	// MPEG1 slice header
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public short[] sliceHeaderBits = new short[44] { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1, 0,1,0,0,0, 0,255,255,255,255,255,255};
	public short[] sliceHeaderBits = new short[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1, 0,1,0,0,0, 0,255,255,255,255,255,255};

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public short[] EOPBits = new short[8] {255,255,255,255,255,255,255,255};
	public short[] EOPBits = new short[] {255,255,255,255,255,255,255,255};

	private byte[] byteBuffer = new byte[] {0};
	
	public MPEGFunctions()
	{
		// Calculate frequently used cosine matrix
//		for (int i = 0; i < 8; i++)
//			for (int j = 0; j < 8; j++)
//				cosine[j][i] = (float)Math.cos(Math.PI * j * (2.0 * i + 1) / 16.0);
//		for (int i = 0; i < 8; i++)
//		{
//			System.out.print("{");
//			for (int j = 0; j < 8; j++)
//				System.out.print("" + cosine[i][j] + "f,");
//			System.out.println("},");
//		}
	}

	//	The memory stream can be used to store the encoded picture
	//	by using the writeToMS function anytime a block is encoded
	public final ByteArrayOutputStream getMS()
	{
		return ms;
	}

	//	Perform the DCT on the A block
	//	See the JPEG DCT definition for the formula
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public double[,] calculateDCT(short[,] A)
	public final double[][] calculateDCT(short[][] A)
	{
		int k1, k2, i, j;
		double Cu, Cv;
		double[][] B = new double[8][8];

		for (k1 = 0; k1 < 8; k1++)
		{
			for (k2 = 0; k2 < 8; k2++)
			{
				B[k1][k2] = 0.0;
				for (i = 0; i < 8; i++)
				{
					for (j = 0; j < 8; j++)
					{
						B[k1][k2] += A[i][j] * cosine[k1][i] * cosine[k2][j];
					}
				}

				if (k1 == 0)
				{
					Cu = SQRT2o2;
				}
				else
				{
					Cu = 1.0;
				}
				if (k2 == 0)
				{
					Cv = SQRT2o2;
				}
				else
				{
					Cv = 1.0;
				}

				B[k1][k2] *= (0.25 * Cu * Cv);
			}
		}
		return B; // Return Frequency Component matrix
	}

	// Quantize the DCT coefficients using the defaultQ array
	// In MPEG, always divide the DC value by eight
	public final int[][] Quantize(double[][] S)
	{
		int i, j;
		int[][] S1 = new int[8][8];

		for (i = 0; i < 8; i++)
		{
			for (j = 0; j < 8; j++)
			{
				S1[i][j] = (int) Math.round((S[i][j] / (double) defaultQ[i][j]));
			}
		}

		return S1; // Return quantized Frequency Component matrix
	}


	// Quantize the DCT coefficients using the supplied Q array
	// In MPEG, always divide the DC value by eight
	public final int[][] Quantize(double[][] S, double[][] Q)
	{
		int i, j;
		int[][] S1 = new int[8][8];

		for (i = 0; i < 8; i++)
		{
			for (j = 0; j < 8; j++)
			{
				S1[i][j] = (int) Math.round((S[i][j] / Q[i][j]));
			}
		}

		return S1; // Return quantized Frequency Component matrix
	}


	//	Function to encode the DC values
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public short[] DCHuffmanEncode(int DC, int[] eco, int[] esi)
	public final short[] DCHuffmanEncode(int DC, int[] eco, int[] esi)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[] DCbits = new short[24];
		short[] DCbits = new short[24];
		int cat, tempval, i;
		boolean invert;
		int tempbits;

		for (i = 0; i < 24; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: DCbits[i] = 255;
			DCbits[i] = 255;
		}

		// Set flag if DC is negative
		invert = false;
		if (DC < 0)
		{
			invert = true;
		}
		DC = Math.abs(DC);

		// Determine the number of bits needed to represent DC
		cat = 0;
		tempval = DC;
		while (tempval >= 1)
		{
			cat++;
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			tempval >>= 1;
		}

		// Write bits for Huffman code into DCbits[] array
		tempval = (int) Math.pow(2, esi[cat] - 1);
		for (i = 0; i < esi[cat]; i++)
		{
			tempbits = eco[cat] & tempval;
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: DCbits[i] = (short)(tempbits >> (esi[cat] - 1 - i));
			DCbits[i] = (short)(tempbits >> (esi[cat] - 1 - i));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			tempval >>= 1;
		}

		// Write bits for DC into DCbits[] array
		tempval = (int) Math.pow(2, cat - 1);
		for (i = esi[cat]; i < esi[cat] + cat; i++)
		{
			tempbits = DC & tempval;
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: DCbits[i] = (short)(tempbits >> (cat-1-i+esi[cat]));
			DCbits[i] = (short)(tempbits >> (cat - 1 - i + esi[cat]));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			tempval >>= 1;
		}

		// If necessary, invert bits to represent negative DC
		if (invert)
		{
			for (i = esi[cat]; i < esi[cat] + cat; i++)
			{
				if (DCbits[i] == 0)
				{
					DCbits[i] = 1;
				}
				else
				{
					DCbits[i] = 0;
				}
			}
		}

		return DCbits; // Return array of bits representing DC value
	}


	//	Re order all coefficients after DCT is performed
	public final int[] Zigzag(int[][] S1)
	{
		int[] index = new int[] {0,1,7,8,-7,-7,1,7,7,7,8, -7,-7,-7,-7,1,7,7,7,7,7,8, -7,-7,-7,-7,-7,-7,1,7,7,7,7,7,7,7,1, -7,-7,-7,-7,-7,-7,8,7,7,7,7,7,1, -7,-7,-7,-7,8,7,7,7,1, -7,-7,8,7,1};
		int i;
		int[] zz = new int[64];
		int a, b;
		int S1pointer = 0;

		for (i = 0; i < 64; i++)
		{
			S1pointer += index[i];
			a = S1pointer / 8;
			b = S1pointer % 8;
			zz[i] = S1[a][b];
		}

		return zz; // Return sorted array with DC value at position 0
	}


	//	This function takes the array pointed to by zz and encodes
	//	the values using the Huffman code pointed to by eco[].
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public short[] ACHuffmanEncode(int[] zz)
	public final short[] ACHuffmanEncode(int[] zz)
	{
		int MAXACSIZE = 1764; // 63 AC Values * 28 bits
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[] outBits = new short[MAXACSIZE];
		short[] outBits = new short[MAXACSIZE];
		int run, tempval;
		int ACindex = 0;
		int i, AC;
		int arrayPosition;
		int ACbits;
		int size;
		int code;
		int outbitsPosition;
		int tempbits;

		for (i = 0; i < MAXACSIZE; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: outBits[i] = 255;
			outBits[i] = 255;
		}

		//	Start at zz[1] since don't want to include DC value
		//	Do this to the end of the zz array
		arrayPosition = 1;
		outbitsPosition = 0;
		while (ACindex < MAXACSIZE - 28)
		{
			// First figure out how many consecutive zeros
			run = 0;
			while ((zz[arrayPosition] == 0) && (arrayPosition < 63))
			{
				run++;
				arrayPosition++;
			}

			// Read in the AC value after the zeros
			AC = zz[arrayPosition];
			arrayPosition++;

			//	Set up exit condition from loop if at end of block
			if (arrayPosition >= 63)
			{
				//	Reset other values
				AC = 0;
				run = 0;
				ACbits = ACindex;
				ACindex = MAXACSIZE + 200;
			}

			//	Determine Huffman code and number of bits needed to
			//	represent the run,level combination.  See MPEG1 spec.
			if ((run <= 31) && (Math.abs(AC) == 1))
			{
				code = ACcode[run];
				size = ACsize[run];
				//	If AC is negative, the sign bit in the Huffman code
				//	equals 1, else it equals 0. So if negative, add 1 to code.
				if (AC < 0)
				{
					code += 1;
				}
			}
			else if ((run <= 16) && (Math.abs(AC) == 2))
			{
				code = ACcode[32 + run];
				size = ACsize[32 + run];
				if (AC < 0)
				{
					code += 1;
				}
			}
			else if ((run <= 6) && (Math.abs(AC) == 3))
			{
				code = ACcode[49 + run];
				size = ACsize[49 + run];
				if (AC < 0)
				{
					code += 1;
				}
			}
			else if ((run <= 3) && (Math.abs(AC) == 4))
			{
				code = ACcode[56 + run];
				size = ACsize[56 + run];
				if (AC < 0)
				{
					code += 1;
				}
			}
			else if ((run <= 2) && (Math.abs(AC) == 5))
			{
				code = ACcode[60 + run];
				size = ACsize[60 + run];
				if (AC < 0)
				{
					code += 1;
				}
			}
			else if ((run <= 1) && ((Math.abs(AC) >= 6) && (Math.abs(AC) <= 18)))
			{
				code = ACcode[63 + run + ((Math.abs(AC) - 6) * 2)];
				size = ACsize[63 + run + ((Math.abs(AC) - 6) * 2)];
				if (AC < 0)
				{
					code += 1;
				}
			}
			else if ((run == 0) && ((Math.abs(AC) >= 19) && (Math.abs(AC) <= 40)))
			{
				code = ACcode[89 + Math.abs(AC) - 19];
				size = ACsize[89 + Math.abs(AC) - 19];
				if (AC < 0)
				{
					code += 1;
				}
			}
			else if ((run == 0) && (AC == 0)) // EOB condition
			{
				code = 2;
				size = 2;
			}
			else
			{
				code = escapecode(run, AC);
				if (Math.abs(AC) >= 128)
				{
					size = 28;
				}
				else
				{
					size = 20;
				}
			}

			// Write bits for Huffman code into bits[] array
			tempval = (int) Math.pow(2,size-1);
			for (i = 0; i < size; i++)
			{
				tempbits = code & tempval;
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: outBits[i+outbitsPosition] = (short)(tempbits >> (size-i-1));
				outBits[i + outbitsPosition] = (short)(tempbits >> (size - i - 1));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
				tempval >>= 1;
			}

			outbitsPosition += size;

			// Increase index for bits array (don't exceed array size) 
			ACindex += size;
		}

		return outBits;
	}

	//	Function that calculates the Huffman code value for MPEG run,level
	//	combinations that don't have defined codes.
	private int escapecode(int run, int AC)
	{
		int intval = 0;
		int code = 0;

		//	Last 8 or 16 bits in the code are the AC value
		//	For positve values, use normal binary encoding.
		//	For negative values from -1 to -127, use 2's complement value.
		//	For negative values from -128 to -255, use 16 bit value with MSB=1,
		//	and 2's complement of AC.
		if (AC > 0)
		{
			intval = AC;
		}
		else
		{
			if (AC >= -127)
			{
				intval = 256 + AC;
			}
			else
			{
				intval = 32768 + 256 + AC;
			}
		}

		//	Construct escape code using 000001 as first 6 bits, 
		//	binary representation of run as next 6 bits,
		//	and either 8 or 16 bits representation of AC from above.
		if (Math.abs(AC) < 128)
		{
			code = 16384 + 256 * run + intval;
		}
		else
		{
			code = 4194304 + 65536 * run + intval;
		}

		return (code);
	}

	// Write the Macroblock header for I frame blocks
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void writeMbHeader(short[] left)
	public final void writeMbHeader(short[] left)
	{
		int i = 0;

		//	Search leftoverBits[] for first -1 value
		while (left[i] != 255)
		{
			i++;
		}

		//	Write MB Header values '1 1' to leftoverBits[]
		left[i++] = 1;
		left[i] = 1;
	}

	//	Function to take the encoded bits from the latest block and
	//	write them to a memory stream
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void writeToMS(short[] left, short[] DC, short[] AC, ref long outBytes)
	public final void writeToMS(short[] left, short[] DC, short[] AC, RefObject<Long> outBytes) throws IOException
	{
		int i, j, bytevalue, leftBits;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[] bitArray = new short[2100];
		short[] bitArray = new short[2100];
		int bitIndex = 0;
		int outputbytes = 0;

		// The worst case is 63 AC values that are each 28 bits long 
		// plus a 24 bit DC value
		for (i = 0; i < 2100; i++)
		{
			bitArray[i] = 0;
		}

		//	Write the leftover bits from the previous block to bitArray[]
		i = 0;
		while (left[i] != 255)
		{
			bitArray[bitIndex++] = left[i++];
		}

		//	Write the DC bits from the current block to bitArray[]
		i = 0;
		while (DC[i] != 255)
		{
			bitArray[bitIndex++] = DC[i++];
		}

		//	Write the AC bits from the current block to bitArray[]
		i = 0;
		while (AC[i] != 255)
		{
			bitArray[bitIndex++] = AC[i++];
		}

		//	Calculate the number of bytes to write to the MemoryStream and 
		//	how many bits will be left over
		outputbytes = bitIndex / 8;
		leftBits = bitIndex % 8;

		//	Write the bytes to the MemoryStream
		for (i = 0; i < outputbytes; i++)
		{
			bytevalue = 0;
			for (j = 0; j < 8; j++)
			{
				bytevalue += (int)((bitArray[i * 8 + j] * Math.pow(2,(7 - j))));
			}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ms.WriteByte((short) bytevalue);
			byteBuffer[0] = (byte) bytevalue;
			ms.write(byteBuffer);
		}

		//	Store the leftover bits in left[] and fill the rest with -1's
		for (i = 0; i < leftBits; i++)
		{
			left[i] = bitArray[outputbytes * 8 + i];
		}
		for (i = leftBits; i < 10; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: left[i] = 255;
			left[i] = 255;
		}

		outBytes.argValue += outputbytes;
	}

	//	Function to write the final leftover bits to the memoryStream and
	//	then stuff with 0's to short align for next picture
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void writeEOP(short[] left, short[] EOP)
	public final void writeEOP(short[] left, short[] EOP) throws IOException
	{
		int i;
		int j, leftBits;
		int bytevalue;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[] bitArray = new short[20];
		short[] bitArray = new short[20];
		int bitIndex = 0;
		int outputbytes = 0;

		for (i = 0; i < 20; i++)
		{
			bitArray[i] = 0;
		}

		//	Write the leftover bits from the previous block to bitArray[]
		i = 0;
		while (left[i] != 255)
		{
			bitArray[bitIndex++] = left[i++];
		}

		//	Write the EOP bits from the current block to bitArray[]
		i = 0;
		while (EOP[i] != 255)
		{
			bitArray[bitIndex++] = EOP[i++];
		}

		//	Calculate the number of bytes to write to the MemoryStream and 
		//	how many bits will be left over
		outputbytes = bitIndex / 8;
		leftBits = bitIndex % 8;

		//	If one full short needs to be written
		if (outputbytes == 1)
		{
			bytevalue = 0;
			for (j = 0; j < 8; j++)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: bytevalue = (short)(2*bytevalue + bitArray[j]);
				bytevalue = (short)(2 * bytevalue + bitArray[j]);
			}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ms.WriteByte((short) bytevalue);
			byteBuffer[0] = (byte) bytevalue;
			ms.write(byteBuffer);
		}
			//	Else pad leftover bits with zeros and write to MemoryStream
		else
		{
			bytevalue = 0;
			for (j = 0; j < 8; j++)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: bytevalue = (short)(2*bytevalue + bitArray[j]);
				bytevalue = (short)(2 * bytevalue + bitArray[j]);
			}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ms.WriteByte((short) bytevalue);
			byteBuffer[0] = (byte) bytevalue;
			ms.write(byteBuffer);
		}

		//	Leftover is cleared so re-initialize leftover bits to -1's
		for (j = 0; j < 10; j++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: left[j] = 255;
			left[j] = 255;
		}
	}

	
	
	
	//TODO: figure out BufferedImage business and optimize
	
	
	// Function to get the Y values for an RGB macroblock
	// pointed to by [vblock,hblock] out of BufferedImage img
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public short[,] getYMatrix(BufferedImage img, int vblock, int hblock)
	public final short[][] getYMatrix(BufferedImage img, int vblock, int hblock)
	{
		int i, j;
		double tempdouble;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[,] Y = new short[16,16];
		short[][] Y = new short[16][16];

		for (i = 0; i < 16; i++)
		{
			for (j = 0; j < 16; j++)
			{
				//System.out.println((hblock * 16 + j * 2) + " : " + (vblock * 16 + i * 2));
				int rgb = img.getRGB((hblock * 16 + j),(vblock * 16 + i));
				tempdouble = (219.0 * (
					0.59 * ((rgb >> 16) & 0x0ff) + //R
					0.30 * ((rgb >> 8) & 0x0ff) +  //G...
					0.11 * (rgb & 0x0ff)) / 255.0) + 16.0;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Y[i,j] = (short)(Math.Round(tempdouble));
				Y[i][j] = (short)(Math.round(tempdouble)); // Y is limited from 16 to 235
			}
		}
		return Y;
	}

	// Function to get the CR values for an RGB macroblock
	// pointed to by [vblock,hblock] out of BufferedImage img
	// In MPEG1, use subsampling to make one 8x8 block of Pr values
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public short[,] getCRMatrix(BufferedImage img, int vblock, int hblock)
	public final short[][] getCRMatrix(BufferedImage img, int vblock, int hblock)
	{
		int i, j;
		double tempdouble;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[,] CR = new short[8,8];
		short[][] CR = new short[8][8];

		for (i = 0; i < 8; i++)
		{
			for (j = 0; j < 8; j++)
			{
				int rgb = img.getRGB((hblock * 16 + j * 2),(vblock * 16 + i * 2));
				tempdouble = (224.0 * (
					0.50 * ((rgb >> 16) & 0x0ff) - //R
					0.42 * ((rgb >> 8) & 0x0ff) -  //G...
					0.08 * (rgb & 0x0ff)) / 255.0) + 128.0;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: CR[i,j] = (short)(Math.Round(tempdouble));
				CR[i][j] = (short)(Math.round(tempdouble)); // Cr is limited from 16 to 235
			}
		}
		return CR;
	}

	// Function to get the CB values for an RGB macroblock
	// pointed to by [vblock,hblock] out of BufferedImage img
	// In MPEG1, use subsampling to make one 8x8 block of Pb values
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public short[,] getCBMatrix(BufferedImage img, int vblock, int hblock)
	public final short[][] getCBMatrix(BufferedImage img, int vblock, int hblock)
	{
		int i, j;
		double tempdouble;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[,] CB = new short[8,8];
		short[][] CB = new short[8][8];

		for (i = 0; i < 8; i++)
		{
			for (j = 0; j < 8; j++)
			{
				int rgb = img.getRGB((hblock * 16 + j * 2),(vblock * 16 + i * 2));
				tempdouble = (224.0 * (
					-0.17 * ((rgb >> 16) & 0x0ff) - //R
					0.33 * ((rgb >> 8) & 0x0ff) +   //G...
					0.50 * (rgb & 0x0ff)) / 255.0) + 128.0;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: CB[i,j] = (short)(Math.Round(tempdouble));
				CB[i][j] = (short)(Math.round(tempdouble)); // Cb is limited from 16 to 235
			}
		}
		return CB;
	}
	//*/
}

// Cb is limited from 16 to 235