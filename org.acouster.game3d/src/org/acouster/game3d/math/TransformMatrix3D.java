package org.acouster.game3d.math;


public class TransformMatrix3D
{
	public float
		value_0_, value_1_, value_2_, value_3_,
		value_4_, value_5_, value_6_, value_7_,
		value_8_, value_9_, value_10_, value_11_,
		value_12_, value_13_, value_14_, value_15_;
	
	public TransformMatrix3D()
	{
		loadIdentity();
	}
	
	public void mul(TransformMatrix3D m)
	{
		float m11 = value_0_*m.value_0_ + value_1_*m.value_4_ + value_2_*m.value_8_ + value_3_*m.value_12_;
		float m12 = value_0_*m.value_1_ + value_1_*m.value_5_ + value_2_*m.value_9_ + value_3_*m.value_13_;
		float m13 = value_0_*m.value_2_ + value_1_*m.value_6_ + value_2_*m.value_10_ + value_3_*m.value_14_;
		float m14 = value_0_*m.value_3_ + value_1_*m.value_7_ + value_2_*m.value_11_ + value_3_*m.value_15_;
		float m21 = value_4_*m.value_0_ + value_5_*m.value_4_ + value_6_*m.value_8_ + value_7_*m.value_12_;
		float m22 = value_4_*m.value_1_ + value_5_*m.value_5_ + value_6_*m.value_9_ + value_7_*m.value_13_;
		float m23 = value_4_*m.value_2_ + value_5_*m.value_6_ + value_6_*m.value_10_ + value_7_*m.value_14_;
		float m24 = value_4_*m.value_3_ + value_5_*m.value_7_ + value_6_*m.value_11_ + value_7_*m.value_15_;
		float m31 = value_8_*m.value_0_ + value_9_*m.value_4_ + value_10_*m.value_8_ + value_11_*m.value_12_;
		float m32 = value_8_*m.value_1_ + value_9_*m.value_5_ + value_10_*m.value_9_ + value_11_*m.value_13_;
		float m33 = value_8_*m.value_2_ + value_9_*m.value_6_ + value_10_*m.value_10_ + value_11_*m.value_14_;
		float m34 = value_8_*m.value_3_ + value_9_*m.value_7_ + value_10_*m.value_11_ + value_11_*m.value_15_;
		float m41 = value_12_*m.value_0_ + value_13_*m.value_4_ + value_14_*m.value_8_ + value_15_*m.value_12_;
		float m42 = value_12_*m.value_1_ + value_13_*m.value_5_ + value_14_*m.value_9_ + value_15_*m.value_13_;
		float m43 = value_12_*m.value_2_ + value_13_*m.value_6_ + value_14_*m.value_10_ + value_15_*m.value_14_;
		float m44 = value_12_*m.value_3_ + value_13_*m.value_7_ + value_14_*m.value_11_ + value_15_*m.value_15_;
		value_0_ = m11;
		value_1_ = m12;
		value_2_ = m13;
		value_3_ = m14;
		value_4_ = m21;
		value_5_ = m22;
		value_6_ = m23;
		value_7_ = m24;
		value_8_ = m31;
		value_9_ = m32;
		value_10_ = m33;
		value_11_ = m34;
		value_12_ = m41;
		value_13_ = m42;
		value_14_ = m43;
		value_15_ = m44;
	}
	
	public void mulMByMe(TransformMatrix3D m)
	{
		float m11 = m.value_0_*value_0_ + m.value_1_*value_4_ + m.value_2_*value_8_ + m.value_3_*value_12_;
		float m12 = m.value_0_*value_1_ + m.value_1_*value_5_ + m.value_2_*value_9_ + m.value_3_*value_13_;
		float m13 = m.value_0_*value_2_ + m.value_1_*value_6_ + m.value_2_*value_10_ + m.value_3_*value_14_;
		float m14 = m.value_0_*value_3_ + m.value_1_*value_7_ + m.value_2_*value_11_ + m.value_3_*value_15_;
		float m21 = m.value_4_*value_0_ + m.value_5_*value_4_ + m.value_6_*value_8_ + m.value_7_*value_12_;
		float m22 = m.value_4_*value_1_ + m.value_5_*value_5_ + m.value_6_*value_9_ + m.value_7_*value_13_;
		float m23 = m.value_4_*value_2_ + m.value_5_*value_6_ + m.value_6_*value_10_ + m.value_7_*value_14_;
		float m24 = m.value_4_*value_3_ + m.value_5_*value_7_ + m.value_6_*value_11_ + m.value_7_*value_15_;
		float m31 = m.value_8_*value_0_ + m.value_9_*value_4_ + m.value_10_*value_8_ + m.value_11_*value_12_;
		float m32 = m.value_8_*value_1_ + m.value_9_*value_5_ + m.value_10_*value_9_ + m.value_11_*value_13_;
		float m33 = m.value_8_*value_2_ + m.value_9_*value_6_ + m.value_10_*value_10_ + m.value_11_*value_14_;
		float m34 = m.value_8_*value_3_ + m.value_9_*value_7_ + m.value_10_*value_11_ + m.value_11_*value_15_;
		float m41 = m.value_12_*value_0_ + m.value_13_*value_4_ + m.value_14_*value_8_ + m.value_15_*value_12_;
		float m42 = m.value_12_*value_1_ + m.value_13_*value_5_ + m.value_14_*value_9_ + m.value_15_*value_13_;
		float m43 = m.value_12_*value_2_ + m.value_13_*value_6_ + m.value_14_*value_10_ + m.value_15_*value_14_;
		float m44 = m.value_12_*value_3_ + m.value_13_*value_7_ + m.value_14_*value_11_ + m.value_15_*value_15_;
		value_0_ = m11;
		value_1_ = m12;
		value_2_ = m13;
		value_3_ = m14;
		value_4_ = m21;
		value_5_ = m22;
		value_6_ = m23;
		value_7_ = m24;
		value_8_ = m31;
		value_9_ = m32;
		value_10_ = m33;
		value_11_ = m34;
		value_12_ = m41;
		value_13_ = m42;
		value_14_ = m43;
		value_15_ = m44;
	}
	
	public void transform(VectorXYZ dest, VectorXYZ v)
	{
		float xxx = value_0_*v.x + value_1_*v.y + value_2_*v.z + value_3_*v.w;
		float yyy = value_4_*v.x + value_5_*v.y + value_6_*v.z + value_7_*v.w;
		float zzz = value_8_*v.x + value_9_*v.y + value_10_*v.z + value_11_*v.w;
		float www = value_12_*v.x + value_13_*v.y + value_14_*v.z + value_15_*v.w;
		
		dest.x = xxx;
		dest.y = yyy;
		dest.z = zzz;
		dest.w = www;
	}
	public static int nCalled = 0;
	public void transformNoW(VectorXYZ dest, VectorXYZ v)
	{
		float xxx = value_0_*v.x + value_1_*v.y + value_2_*v.z + value_3_*v.w;
		float yyy = value_4_*v.x + value_5_*v.y + value_6_*v.z + value_7_*v.w;
		float zzz = value_8_*v.x + value_9_*v.y + value_10_*v.z + value_11_*v.w;
		
		dest.x = xxx;
		dest.y = yyy;
		dest.z = zzz;
		
		nCalled++;
	}
	public void transformNoWNoZ(VectorXYZ dest, VectorXYZ v)
	{
		float xxx = value_0_*v.x + value_1_*v.y + value_2_*v.z + value_3_*v.w;
		float yyy = value_4_*v.x + value_5_*v.y + value_6_*v.z + value_7_*v.w;
		
		dest.x = xxx;
		dest.y = yyy;
	}
	public void transformJustZ(VectorXYZ dest, VectorXYZ v)
	{
		dest.z = value_8_*v.x + value_9_*v.y + value_10_*v.z + value_11_*v.w;
	}
	public void transform(VectorXYZ v)
	{
		float xxx = value_0_*v.x + value_1_*v.y + value_2_*v.z + value_3_*v.w;
		float yyy = value_4_*v.x + value_5_*v.y + value_6_*v.z + value_7_*v.w;
		float zzz = value_8_*v.x + value_9_*v.y + value_10_*v.z + value_11_*v.w;
		float www = value_12_*v.x + value_13_*v.y + value_14_*v.z + value_15_*v.w;
		
		v.x = xxx;
		v.y = yyy;
		v.z = zzz;
		v.w = www;
	}
	public void rotateAboutY(float ay)
	{
		float cos = (float)Math.cos(ay);
		float sin = (float)Math.sin(ay);
		
		float lefty = value_0_*cos + value_2_*sin;
		float righty = -value_0_*sin + value_2_*cos;
		value_0_ = lefty;
		value_2_ = righty;
		lefty = value_4_*cos + value_6_*sin;
		righty = -value_4_*sin + value_6_*cos;
		value_4_ = lefty;
		value_6_ = righty;
		lefty = value_8_*cos + value_10_*sin;
		righty = -value_8_*sin + value_10_*cos;
		value_8_ = lefty;
		value_10_ = righty;
		lefty = value_12_*cos + value_14_*sin;
		righty = -value_12_*sin + value_14_*cos;
		value_12_ = lefty;
		value_14_ = righty;
	}
	
	public void loadIdentity()
	{
		value_0_ = 1;
		value_1_ = 0;
		value_2_ = 0;
		value_3_ = 0;
		value_4_ = 0;
		value_5_ = 1;
		value_6_ = 0;
		value_7_ = 0;
		value_8_ = 0;
		value_9_ = 0;
		value_10_ = 1;
		value_11_ = 0;
		value_12_ = 0;
		value_13_ = 0;
		value_14_ = 0;
		value_15_ = 1;
	}
	
	/**
	 * last column of this = this*[x y z]
	 */
	public void translateTo(float x, float y, float z)
	{
		value_3_ = value_0_*x + value_1_*y + value_2_*z + value_3_;
		value_7_ = value_4_*x + value_5_*y + value_6_*z + value_7_;
		value_11_ = value_8_*x + value_9_*y + value_10_*z + value_11_;
		value_15_ = value_12_*x + value_13_*y + value_14_*z + value_15_;
	}
	
	public static TransformMatrix3D generateTranslation(float x, float y, float z)
	{
		TransformMatrix3D m = new TransformMatrix3D();
		m.translateTo(x, y, z);
		return m;
	}
	public static TransformMatrix3D generateRotationAboutY(float ay)
	{
		TransformMatrix3D m = new TransformMatrix3D();
		m.rotateAboutY(ay);
		return m;
	}
	
	public void setTransform(TransformMatrix3D transform)
	{
		this.value_0_ = transform.value_0_;
		this.value_1_ = transform.value_1_;
		this.value_2_ = transform.value_2_;
		this.value_3_ = transform.value_3_;
		this.value_4_ = transform.value_4_;
		this.value_5_ = transform.value_5_;
		this.value_6_ = transform.value_6_;
		this.value_7_ = transform.value_7_;
		this.value_8_ = transform.value_8_;
		this.value_9_ = transform.value_9_;
		this.value_10_ = transform.value_10_;
		this.value_11_ = transform.value_11_;
		this.value_12_ = transform.value_12_;
		this.value_13_ = transform.value_13_;
		this.value_14_ = transform.value_14_;
		this.value_15_ = transform.value_15_;
	}

	
	public String toString()
	{
		String result = "";
		result += "\t" + ((int)(1000*value_0_)/1000.0f) + "\t" + ((int)(1000*value_1_)/1000.0f) + "\t" + ((int)(1000*value_2_)/1000.0f) + "\t" + ((int)(1000*value_3_)/1000.0f) + "\n";
		result += "\t" + ((int)(1000*value_4_)/1000.0f) + "\t" + ((int)(1000*value_5_)/1000.0f) + "\t" + ((int)(1000*value_6_)/1000.0f) + "\t" + ((int)(1000*value_7_)/1000.0f) + "\n";
		result += "\t" + ((int)(1000*value_8_)/1000.0f) + "\t" + ((int)(1000*value_9_)/1000.0f) + "\t" + ((int)(1000*value_10_)/1000.0f) + "\t" + ((int)(1000*value_11_)/1000.0f) + "\n";
		result += "\t" + ((int)(1000*value_12_)/1000.0f) + "\t" + ((int)(1000*value_13_)/1000.0f) + "\t" + ((int)(1000*value_14_)/1000.0f) + "\t" + ((int)(1000*value_15_)/1000.0f) + "\n";
		return result;
	}
	
	/**
	 * @param mBySide
	 * @return a string representation of this matrix and mBySide, side-by-side
	 */
	public String toString(TransformMatrix3D mBySide)
	{
		String result = "";
		result += "\t" + ((int)(1000*value_0_)/1000.0f) + "\t" + ((int)(1000*value_1_)/1000.0f) + "\t" + ((int)(1000*value_2_)/1000.0f) + "\t" + ((int)(1000*value_3_)/1000.0f) + "\t|\t" + ((int)(1000*mBySide.value_0_)/1000.0f) + "\t" + ((int)(1000*mBySide.value_1_)/1000.0f) + "\t" + ((int)(1000*mBySide.value_2_)/1000.0f) + "\t" + ((int)(1000*mBySide.value_3_)/1000.0f) + "\n";
		result += "\t" + ((int)(1000*value_4_)/1000.0f) + "\t" + ((int)(1000*value_5_)/1000.0f) + "\t" + ((int)(1000*value_6_)/1000.0f) + "\t" + ((int)(1000*value_7_)/1000.0f) + "\t|\t" + ((int)(1000*mBySide.value_4_)/1000.0f) + "\t" + ((int)(1000*mBySide.value_5_)/1000.0f) + "\t" + ((int)(1000*mBySide.value_6_)/1000.0f) + "\t" + ((int)(1000*mBySide.value_7_)/1000.0f) + "\n";
		result += "\t" + ((int)(1000*value_8_)/1000.0f) + "\t" + ((int)(1000*value_9_)/1000.0f) + "\t" + ((int)(1000*value_10_)/1000.0f) + "\t" + ((int)(1000*value_11_)/1000.0f) + "\t|\t" + ((int)(1000*mBySide.value_8_)/1000.0f) + "\t" + ((int)(1000*mBySide.value_9_)/1000.0f) + "\t" + ((int)(1000*mBySide.value_10_)/1000.0f) + "\t" + ((int)(1000*mBySide.value_11_)/1000.0f) + "\n";
		result += "\t" + ((int)(1000*value_12_)/1000.0f) + "\t" + ((int)(1000*value_13_)/1000.0f) + "\t" + ((int)(1000*value_14_)/1000.0f) + "\t" + ((int)(1000*value_15_)/1000.0f) + "\t|\t" + ((int)(1000*mBySide.value_12_)/1000.0f) + "\t" + ((int)(1000*mBySide.value_13_)/1000.0f) + "\t" + ((int)(1000*mBySide.value_14_)/1000.0f) + "\t" + ((int)(1000*mBySide.value_15_)/1000.0f) + "\n";
		return result;
	}
	
	//================= flyby3D conventions ========================
	
	protected float ay;
	
	public void flyby3DConvention_setLocation(float x, float y, float z)
	{
		value_3_ = x;
		value_7_ = y;
		value_11_ = z;
	}
	public void flyby3DConvention_setLocation(double x, double y, double z)
	{
		value_3_ = (float)x;
		value_7_ = (float)y;
		value_11_ = (float)z;
	}
	public void flyby3DConvention_vectorMove(double x, double y, double z, double v)
	{
		value_3_ += (float)(x*v);
		value_7_ += (float)(y*v);
		value_11_ += (float)(z*v);
	}
	public void flyby3DConvention_setAy(double ay)
	{
		this.ay = (float)ay;
		
		float cos = (float)Math.cos(ay);
		float sin = (float)Math.sin(ay);
		
		value_0_ = cos;
		value_10_ = cos;
		value_2_ = -sin;
		value_8_ = sin;
	}
	public float flyby3DConvention_getX()
	{
		return value_3_;
	}
	public float flyby3DConvention_getY()
	{
		return value_7_;
	}
	public float flyby3DConvention_getZ()
	{
		return value_11_;
	}
	public float flyby3DConvention_getAy()
	{
		return ay;
	}
	/**
	 * @param dest = this * (last column in objectTransform)
	 * @param objectTransform
	 */
	public void flyby3DConvention_transformJustLocation(VectorXYZ dest, TransformMatrix3D objectTransform)
	{
		float xxx = value_0_*objectTransform.value_3_ + value_1_*objectTransform.value_7_ + value_2_*objectTransform.value_11_ + value_3_;
		float yyy = value_4_*objectTransform.value_3_ + value_5_*objectTransform.value_7_ + value_6_*objectTransform.value_11_ + value_7_;
		float zzz = value_8_*objectTransform.value_3_ + value_9_*objectTransform.value_7_ + value_10_*objectTransform.value_11_ + value_11_;
		
		dest.x = xxx;
		dest.y = yyy;
		dest.z = zzz;
	}
}
