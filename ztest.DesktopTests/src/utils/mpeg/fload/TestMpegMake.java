package utils.mpeg.fload;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestMpegMake
{
	private long outshorts = 0;
	private java.util.Random r = new java.util.Random();
	private int jxjxjx = 0;

	public void DrawRandomDot(BufferedImage bbb)
	{
		Graphics g = bbb.getGraphics();
		g.setColor(Color.GRAY);
		g.fillRect(0,  0, 640, 480);
		int x1 = (jxjxjx += 10);
		int y1 = 0;
		int x2 = jxjxjx;
		int y2 = 480;
		g.setColor(Color.RED);
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(0, 130, 640, 130);
		g.drawLine(100, 0, 100, 480);
	}
	public void WriteMPEGSequence(String fname, int nPictures, int nFrameRepeat) throws IOException
	{
		FileOutputStream bw = new FileOutputStream(fname);
		//BinaryWriter bw = new BinaryWriter(File.Create(fname));
		byte[] biteBuffer = new byte[] {0};
		
		for (int iii = 0; iii < nPictures; iii++)
		{

		int i;
		int j, k1, k2;
		long j2;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short tempshort;
		short tempshort;

		int ACSIZE = 1764;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[] leftoverBits = new short[10];
		short[] leftoverBits = new short[10];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[] DCbits = new short[24];
		short[] DCbits = new short[24];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[] ACbits = new short[ACSIZE];
		short[] ACbits = new short[ACSIZE];

		int DCY, DCCR, DCCB, lastDCY, lastDCCR, lastDCCB;
		int hblock, vblock;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[,] Y = new short[16, 16];
		short[][] Y = new short[16][16];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[,] CR = new short[8, 8];
		short[][] CR = new short[8][8];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[,] CB = new short[8, 8];
		short[][] CB = new short[8][8];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[,] block = new short[8, 8];
		short[][] block = new short[8][8];
		float[][] S = new float[8][8];
		int[][] Q = new int[8][8];
		int[] ZZ = new int[64];
		long imageshorts = 0;
		long compressedshorts = 0;
		float compressPercent = 0.0f;
		String mBox = null;
		long bitRate;

		MPEGFunctions MPEG = new MPEGFunctions();

		BufferedImage img = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
		DrawRandomDot(img);
		//BufferedImage img = new BufferedImage(ImageIO.read(new File()).FromFile("C:\\Users\\mtemkine\\Desktop\\asdd\\test_0" + (iii % 4 + 1) + ".jpg", true));

		imageshorts = img.getHeight() * img.getWidth() * 3;

		// Create output file and Memory Stream to write encoded image to

		//MemoryStream ms = new MemoryStream();
		//BufferedOutputStream ms = new BufferedOutputStream();

		//	Set up variables to encode image into MPEG frame
		lastDCY = 128;
		lastDCCR = lastDCCB = 128;
		for (i = 0; i < 10; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: leftoverBits[i] = 255;
			leftoverBits[i] = (short)255;
		}
		for (i = 0; i < 24; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ACbits[i] = 255;
			ACbits[i] = (short)255;
		}
		for (i = 0; i < 24; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: DCbits[i] = 255;
			DCbits[i] = (short)255;
		}


			System.out.println(iii);
			//DrawRandomDot(img);


			outshorts = 20;

			// Write MPEG picture and slice headers to MemoryStream
			for (i = 0; i < 10; i++)
			{
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: MPEG.picHeaderBits[i + 32] = (short)((0 & (int)Math.Pow(2, 9 - i)) >> (9 - i));
				MPEG.picHeaderBits[i + 32] = (short)((0 & (int)Math.pow(2, 9 - i)) >> (9 - i));
			}
			RefObject<Long> tempRef_outshorts = new RefObject<Long>(outshorts);
			MPEG.writeToMS(leftoverBits, MPEG.picHeaderBits, ACbits, tempRef_outshorts);
			outshorts = tempRef_outshorts.argValue;
			RefObject<Long> tempRef_outshorts2 = new RefObject<Long>(outshorts);
			MPEG.writeToMS(leftoverBits, MPEG.sliceHeaderBits, ACbits, tempRef_outshorts2);
			outshorts = tempRef_outshorts2.argValue;

			// Do this for each 16x16 pixel block in the BufferedImage file
			for (vblock = 0; vblock < img.getHeight() / 16; vblock++)
			{
				for (hblock = 0; hblock < img.getWidth() / 16; hblock++)
				{
					//	Write 2 bits for Macroblock header to leftoverbits
					//	leftoverbits = '1', '1';
					MPEG.writeMbHeader(leftoverBits);

					//	Fill the Y[] array with a 16x16 block of RGB values
					Y = MPEG.getYMatrix(img, vblock, hblock);
					//	Fill the CR and CB arrays with 8x8 blocks by subsampling 
					//	the RGB array
					CR = MPEG.getCRMatrix(img, vblock, hblock);
					CB = MPEG.getCBMatrix(img, vblock, hblock);

					// First calculate DCTs for the 4 Y blocks
					for (k1 = 0; k1 < 2; k1++)
					{
						for (k2 = 0; k2 < 2; k2++)
						{
							//	Put 8x8 Y blocks into the block[] array and
							//	then calculate the DCT and quantize the result
							for (i = 0; i < 8; i++)
							{
								for (j = 0; j < 8; j++)
								{
									block[i][j] = Y[(k1 * 8 + i)][(k2 * 8 + j)];
								}
							}
							S = MPEG.calculateDCT(block);
							Q = MPEG.Quantize(S);

							//	Section to differentially Huffman encode DC values
							//	DC is the diffential value for the DC coefficient
							//	lastDC is the running total of the full magnitude
							//	Then send the DC value to DCHuffmanEncode
							for (i = 0; i < 24; i++)
							{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: DCbits[i] = 255;
								DCbits[i] = (short)255;
							}
							DCY = Q[0][0] - lastDCY;
							lastDCY += DCY;
							DCbits = MPEG.DCHuffmanEncode(DCY, MPEG.DCLumCode, MPEG.DCLumSize);

							//	Section to encode AC Huffman values
							//	Put the AC coefficients into the ACarray[]
							//	in zigzag order, then Huffman encode the
							//	resulting array.
							for (i = 0; i < ACSIZE; i++)
							{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ACbits[i] = 255;
								ACbits[i] = (short)255;
							}
							ZZ = MPEG.Zigzag(Q);
							ACbits = MPEG.ACHuffmanEncode(ZZ);

							//	Write the encoded bits to the MemoryStream
							RefObject<Long> tempRef_outshorts3 = new RefObject<Long>(outshorts);
							MPEG.writeToMS(leftoverBits, DCbits, ACbits, tempRef_outshorts3);
							outshorts = tempRef_outshorts3.argValue;
						}
					}

					// Now calculate the DCT for the CB array and quantize
					S = MPEG.calculateDCT(CB);
					Q = MPEG.Quantize(S);

					//	Encode DC value
					for (i = 0; i < 24; i++)
					{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: DCbits[i] = 255;
						DCbits[i] = (short)255;
					}
					DCCB = Q[0][0] - lastDCCB;
					lastDCCB += DCCB;
					DCbits = MPEG.DCHuffmanEncode(DCCB, MPEG.DCChromCode, MPEG.DCChromSize);

					//	Encode AC values
					for (i = 0; i < ACSIZE; i++)
					{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ACbits[i] = 255;
						ACbits[i] = (short)255;
					}
					ZZ = MPEG.Zigzag(Q);
					ACbits = MPEG.ACHuffmanEncode(ZZ);

					//	Write the encoded bits to the MemoryStream
					RefObject<Long> tempRef_outshorts4 = new RefObject<Long>(outshorts);
					MPEG.writeToMS(leftoverBits, DCbits, ACbits, tempRef_outshorts4);
					outshorts = tempRef_outshorts4.argValue;

					// Now calculate the DCT for the CR array and quantize
					S = MPEG.calculateDCT(CR);
					Q = MPEG.Quantize(S);

					// Encode DC value
					for (i = 0; i < 24; i++)
					{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: DCbits[i] = 255;
						DCbits[i] = (short)255;
					}
					DCCR = Q[0][0] - lastDCCR;
					lastDCCR += DCCR;
					DCbits = MPEG.DCHuffmanEncode(DCCR, MPEG.DCChromCode, MPEG.DCChromSize);

					//	Encode AC values
					for (i = 0; i < ACSIZE; i++)
					{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ACbits[i] = 255;
						ACbits[i] = (short)255;
					}
					ZZ = MPEG.Zigzag(Q);
					ACbits = MPEG.ACHuffmanEncode(ZZ);

					//	Write the encoded bits to the MemoryStream
					RefObject<Long> tempRef_outshorts5 = new RefObject<Long>(outshorts);
					MPEG.writeToMS(leftoverBits, DCbits, ACbits, tempRef_outshorts5);
					outshorts = tempRef_outshorts5.argValue;
				}
			}

			// Write EOP bits to the MemoryStream
			MPEG.writeEOP(leftoverBits, MPEG.EOPBits);
			outshorts++;

			//	Put memory stream (which contains the encoded image) into buffer
			ByteArrayOutputStream ms = MPEG.getMS();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: short[] buffer = new short[ms.Length];
			byte[] buffer = ms.toByteArray();

			//	Set MPEG Sequence Header bits to correct image size
			j = 2048;
			for (i = 0; i < 12; i++)
			{
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: MPEG.seqHeaderBits[i + 32] = (short)((j & img.getWidth()) >> (11 - i));
				MPEG.seqHeaderBits[i + 32] = (short)((j & img.getWidth()) >> (11 - i));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: MPEG.seqHeaderBits[i + 44] = (short)((j & img.getHeight()) >> (11 - i));
				MPEG.seqHeaderBits[i + 44] = (short)((j & img.getHeight()) >> (11 - i));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
				j >>= 1;
			}

			//	Set MPEG Sequence Header bits to bitRate value
			bitRate = ms.size() * 30 * 8 / 400;
			j2 = 131072;
			for (i = 0; i < 18; i++)
			{
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: MPEG.seqHeaderBits[i + 64] = (short)((j2 & bitRate) >> (17 - i));
				MPEG.seqHeaderBits[i + 64] = (short)((j2 & bitRate) >> (17 - i));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
				j2 >>= 1;
			}

			//	Write MPEG Sequence header to file
			if (iii == 0)
			{
				for (i = 0; i < 12; i++)
				{
					tempshort = 0;
					for (j = 0; j < 8; j++)
					{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: tempshort = (short)(tempshort * 2 + MPEG.seqHeaderBits[i * 8 + j]);
						tempshort = (short)(tempshort * 2 + MPEG.seqHeaderBits[i * 8 + j]);
					}
					biteBuffer[0] = (byte)tempshort;
					bw.write(biteBuffer);
				}
				//	Write MPEG GOP header to file
				for (i = 0; i < 8; i++)
				{
					tempshort = 0;
					for (j = 0; j < 8; j++)
					{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: tempshort = (short)(tempshort * 2 + MPEG.GOPHeaderBits[i * 8 + j]);
						tempshort = (short)(tempshort * 2 + MPEG.GOPHeaderBits[i * 8 + j]);
					}
					bw.write(tempshort);
				}
			}

			//	Fix the picture header for each MPEG frame and write 
			//	the buffer to the file
			for (i = 0; i < nFrameRepeat; i++) {
				for (j = 0; j < 10; j++)
				{
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: MPEG.picHeaderBits[j + 32] = (short)((i & (int)Math.Pow(2, 9 - j)) >> (9 - j));
					MPEG.picHeaderBits[j + 32] = (short)((i & (int)Math.pow(2, 9 - j)) >> (9 - j));
				}
				for (j = 0; j < 4; j++)
				{
					tempshort = 0;
					for (k1 = 0; k1 < 8; k1++)
					{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: tempshort = (short)(2 * tempshort + MPEG.picHeaderBits[j * 8 + k1]);
						tempshort = (short)(2 * tempshort + MPEG.picHeaderBits[j * 8 + k1]);
					}
					buffer[j] = (byte)tempshort;
				}
				bw.write(buffer);
			}

		}

		// Write the End Of Sequence header
		biteBuffer[0] = 0;
		bw.write(biteBuffer);
		bw.write(biteBuffer);
		biteBuffer[0] = 1;
		bw.write(biteBuffer);
		biteBuffer[0] = (byte)0xb7;
		bw.write(biteBuffer);
		bw.close();
	}

}