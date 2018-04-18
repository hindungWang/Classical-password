package code;

import java.util.Scanner;

public class code {

	public static void main(String[] args) {

		String str;
		System.out.println("凯撒密码加密解密：");
		Caesars_code c = new Caesars_code();
		str = c.encode("hello world");//加密字符串
		System.out.println(str);
		System.out.println(c.decode(str));//解密字符串
		
		System.out.println("密钥短语密码加密解密：");
		Shortkey_code s = new Shortkey_code("helvsdfd");//设置短密钥
		str = s.encode("hello world");//加密
		System.out.println(str);
		System.out.println(s.decode(str));//解密
		
		System.out.println("维吉尼亚密码加密解密：");
		Vigenere_code v = new Vigenere_code("RGDFRDRGD");//设置密钥
		str = v.encode("hello world");
		System.out.println(str);//加密
		System.out.println(v.decode(str));//解密
		
		System.out.println("Playfair密码加密解密：");
		Playfair_code p = new Playfair_code("ciiiipher");//设置密钥
		str = p.encode("hello world");
		System.out.println(str);//加密
		System.out.println(p.decode(str));//解密
		
		System.out.println("仿射密码加密解密：");
		Affine_cipher a = new Affine_cipher(7,3);//设置a,b值
		str = a.encode("hello world");
		System.out.println(str);//加密
		System.out.println(a.decode(str));//解密
		
		System.out.println("Hill密码加密解密：");
		Hill h = new Hill(3);//设置方矩阵行列数（字符的分组长度）
		str = h.encode("hello world");//加密
		System.out.println(str);
		System.out.println(h.decode(str));//解密
	}
	static class Caesars_code//1.凯撒密码实现
	{
		private String p = "abcdefghijklmnopqrstuvwxyz";//明文
		private String e = "defghijklmnopqrstuvwxyzabc";//密文
		
		 /*** 
	     * 凯撒密码加密 
	     *  
	     * @param str 需要加密的字符串
	     *            
	     * @return 加密的结果
	     */  
		public String encode(String str)//加密
		{
			String s = new String();
			str = str.toLowerCase();//转化为小写字母
			str = str.replaceAll("[^a-z]", "");//过滤非字母字符
			int i, j;
			char peek;
			for(i = 0;i < str.length();++i)//采用直接一一对应方式
			{
				peek = str.charAt(i);
				for(j = 0;j < p.length();++j)
				{
					if(peek == p.charAt(j))
					{
						break;
					}
				}
				if(j >= e.length())
				{
					s += str.charAt(i);
				}
				else
				{
					s += e.charAt(j);
				}
			}
			return s;
		}
		 /*** 
	     * 凯撒密码解密 
	     *  
	     * @param str 需要解密的字符串
	     *            
	     * @return 解密的结果
	     */  
		public String decode(String str)//解密
		{
			String s = new String();
			str = str.toLowerCase();
			int i, j;
			char peek;
			for(i = 0;i < str.length();++i)
			{
				peek = str.charAt(i);
				for(j = 0;j < e.length();++j)
				{
					if(peek == e.charAt(j))
					{
						break;
					}
				}
				if(j >= e.length())
				{
					s += str.charAt(i);
				}
				else
				{
					s += p.charAt(j);
				}
			}
			return s;
		}
	}
	
	static class Shortkey_code//2.密钥短语密码
	{
		private String shortkey;//短秘钥
		private String p = "abcdefghijklmnopqrstuvwxyz";//明文
		private String e;//待计算密文
		
		Shortkey_code(){};
		/***
		 * 构造函数，初始化短密钥和计算密文
		 * 
		 * @param s 短密钥
		 * 
		 * 
		 * **/
		Shortkey_code(String s)
		{
			this.e = new String();
			this.shortkey = new String();
			s = s.toLowerCase();
			int i, j;
			int peek;
			int count = 0;
			int len = s.length();
			String sk = new String();
			char []c = new char[len];
			for(i = 0;i < len;++i)
			{
				c[i] = s.charAt(i);
			}
			//待进行密钥短语合法性检查  去重复的字符
			for(i = 0;i < len;++i)
			{
				peek = i+1;
				while(peek < len - count)
				{
					if(c[i] == c[peek])
					{
						for(j = peek;j < len - 1;++j)
						{
							c[j] = c[j + 1];//出现重复字母，从peek位置开始将数组往前挪位
						}
						count++;//重复字母出现的次数
						peek--;
					}
					peek++;
				}
			}
			for(i = 0;i < len - count;++i)
			{
				sk += String.valueOf(c[i]);
			}
			this.shortkey = sk;//初始化密钥短语
			this.e += sk;//初始化密文
			int flag;
			for(i = 0;i < p.length();++i)
			{
				flag = 0;
				for(j = 0;j < len - count;++j)
				{
					if(p.charAt(i) == c[j])
					{
						flag = 1;
						break;
					}
				}
				if(flag == 0)
				{
					e += p.charAt(i);
				}
			}
		}
		
		public void printEs()//打印密文和短密钥
		{
			System.out.println(this.shortkey);
			System.out.println(this.e);
		}
		/*** 
	     * 短密钥密码加密 
	     *  
	     * @param str 需要加密的字符串
	     *            
	     * @return 加密的结果
	     */
		public String encode(String str)//加密
		{
			String s = new String();
			int i, j;
			char peek;
			for(i = 0;i < str.length();++i)
			{
				peek = str.charAt(i);
				for(j = 0;j < p.length();++j)
				{
					if(peek == p.charAt(j))
					{
						break;
					}
				}
				if(j >= e.length())
				{
					s += str.charAt(i);
				}
				else
				{
					s += e.charAt(j);
				}
			}
			return s;
		}
		/*** 
	     * 短密钥密码解密 
	     *  
	     * @param str 需要解密的字符串
	     *            
	     * @return 解密的结果
	     */
		public String decode(String str)//解密
		{
			String s = new String();
			int i, j;
			char peek;
			for(i = 0;i < str.length();++i)
			{
				peek = str.charAt(i);
				for(j = 0;j < e.length();++j)
				{
					if(peek == e.charAt(j))
					{
						break;
					}
				}
				if(j >= e.length())
				{
					s += str.charAt(i);
				}
				else
				{
					s += p.charAt(j);
				}
			}
			return s;
		}
	}

	static class Vigenere_code//3.维吉尼亚密码
	{
		private static char []d = {
				'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
		};
		private char [][] dir = new char[26][26];
		private String key;
		
		Vigenere_code() {};
		/**
		 * 构造函数  初始化密钥
		 * 
		 * @param str 密钥字符
		 * 
		 * 
		 * ***/
		
		Vigenere_code(String str)
		{
			str = str.toLowerCase();
			this.key = str;
			int i, j;
			int flag = 0;
			for(i = 0;i < 26;++i)//初始化二维表
			{
				for(j = 0;j < 26;++j)
				{
					dir[i][j] = d[flag%26];
					flag++;
				}
				flag++;
			}
		}
		/**
		 * 
		 * 取当前字符所在字符数组d中的下标
		 * 
		 * @param c 字符
		 * 
		 * @return 下标
		 * 
		 * **/
		public int Indexof(char c)//取下标
		{
			int i;
			for(i = 0;i < 26;++i)
			{
				if(c == d[i])
				{
					return i;
				}
			}
			return -1;
		}
		/**
		 * 
		 * 加密函数
		 * 
		 * @param str 要加密的字符
		 * 
		 * @return 加密后的字符
		 * 
		 * **/
		public String encode(String str)
		{
			str = str.toLowerCase();
			String re = new String();
			int lenstr = str.length();
			int lenkey = this.key.length();//默认密钥长度小于明文长度
			
			int i, j;
			int index_i, index_j;
			char temp;
			for(i = 0,j = 0;i < lenstr; ++i)
			{
				temp = str.charAt(i);
				index_i = Indexof(temp);
				if(index_i == -1)
				{
					re += str.charAt(i);
				}
				else
				{
					temp = this.key.charAt(j%lenkey);
					index_j = Indexof(temp);
					re += dir[index_i][index_j];
					++j;
				}
			}
			return re;
		}
		/***
		 * 解密函数
		 * 
		 * @param str 要解密的字符串
		 * 
		 * @return 解密后的字符串
		 * 
		 * ***/
		public String decode(String str)
		{
			str = str.toLowerCase();
			String re = new String();
			int lenstr = str.length();
			int lenkey = this.key.length();//默认密钥长度小于明文长度
			
			int i, j;
			int index_i,index_j;
			char temp;
			for(i = 0,j = 0;i < lenstr; ++i)
			{
				temp = str.charAt(i);
				index_i = Indexof(temp);
				if(index_i == -1)
				{
					re += temp;
				}
				else
				{
					temp = this.key.charAt(j%lenkey);
					++j;
					index_i = Indexof(temp);
					for(index_j = 0;index_j < 26;++index_j)
					{
						if(dir[index_i][index_j] == str.charAt(i))
						{
							break;
						}
					}
					re += d[index_j];
				}
				
			}
			return re;
		}
	}

	static class Playfair_code//4.多字母替换密码
	{
		private String key;
		
		Playfair_code() {};
		Playfair_code(String str)//i == j
		{
			char[]d = {
					'a','b','c','d','e','f','g','h','i','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
			};
			str = str.toLowerCase();
			str = str.replaceAll("[^a-z]", "");
			str = str.replaceAll("j", "i");
			String sk = new String();
			int i, j, peek;
			int count = 0;
			int len = str.length();
			char []c = new char[len];
			for(i = 0;i < len;++i)
			{
				c[i] = str.charAt(i);
			}
			//待进行密钥短语合法性检查
			for(i = 0;i < len;++i)
			{
				peek = i+1;
				while(peek < len - count)
				{
					if(c[i] == c[peek])
					{
						for(j = peek;j < len - 1;++j)
						{
							c[j] = c[j + 1];//出现重复字母，从peek位置开始将数组往前挪位
						}
						count++;//重复字母出现的次数
						peek--;
					}
					peek++;
				}
			}
			for(i = 0;i < len - count;++i)
			{
				sk += String.valueOf(c[i]);
			}
			for(i= 0;i < 25;++i)
			{
				if(Instr(d[i], sk))
				{
					continue;
				}
				else
				{
					sk += d[i];
				}
			}
			this.key = sk;
		}
		/***
		 * 判断字符是否在str中
		 * 
		 * @param c 所判断的字符
		 * 
		 * @param str 对比的字符串
		 * 
		 * @return 是否在字符串中
		 * 
		 * ***/
		public boolean Instr(char c, String str)
		{
			int i;
			for(i = 0;i < str.length();++i)
			{
				if(str.charAt(i) == c)
				{
					return true;
				}
			}
			return false;
		}
		public int Indexofchar(char c)//i = x%5,j = x/5;
		{
			int i;
			for(i = 0;i < this.key.length();++i)
			{
				if( this.key.charAt(i) == c)
				{
					break;
				}
			}
			return i;
		}
		public char charofindex(int i, int j)
		{
			int index;
			index = i * 5 + j;
			return this.key.charAt(index);
		}
		public StringBuilder changestr(String str)
		{
			str = str.toLowerCase();
			str = str.replaceAll("[^a-z]", "");
			StringBuilder sb=new StringBuilder(str);
			int i;
			for(i = 1;i < sb.length();i += 2)
			{
				if(sb.charAt(i) == sb.charAt(i-1))
				{
					sb.insert(i, 'x');
				}
			}
			if(sb.length()%2 == 1)sb.append('x');
			return sb;
		}
		/**
		 * 
		 * 加密函数
		 * 
		 * @param s 要加密的字符
		 * 
		 * @return 加密后的字符
		 * 
		 * **/
		public String encode(String s)//加密
		{
			StringBuilder str = changestr(s);
			StringBuilder sb = new StringBuilder();
			int i;
			int i_1,j_1,i_2,j_2;
			int index1,index2;
			for(i = 0;i < str.length();i += 2)
			{
				index1 = Indexofchar(str.charAt(i));
				index2 = Indexofchar(str.charAt(i+1));
				i_1 = index1 / 5;
				j_1 = index1 % 5;
				i_2 = index2 / 5;
				j_2 = index2 % 5;
				if(i_1 == i_2)//同行左移
				{
					sb.append(charofindex(i_1, (j_1+1)%5));
					sb.append(charofindex(i_1, (j_2+1)%5));
				}
				else if(j_1 == j_2)//同列下移
				{
					sb.append(charofindex((i_1+1)%5, j_1));
					sb.append(charofindex((i_2+1)%5, j_1));
				}
				else
				{
					sb.append(charofindex(i_1, j_2));//对角线
					sb.append(charofindex(i_2, j_1));
				}
				sb.append(' ');
			}
			return sb.toString();
		}
		/***
		 * 解密函数
		 * 
		 * @param s 要解密的字符串
		 * 
		 * @return 解密后的字符串
		 * 
		 * ***/
		public String decode(String s)//解密
		{
			s = s.toLowerCase();
			s = s.replaceAll(" ", "");
			StringBuilder sb = new StringBuilder();
			int i;
			int i_1,j_1,i_2,j_2;
			int index1,index2;
			int len = s.length();
			for(i = 0;i < len;i += 2)
			{
				index1 = Indexofchar(s.charAt(i));
				index2 = Indexofchar(s.charAt(i+1));
				i_1 = index1 / 5;
				j_1 = index1 % 5;
				i_2 = index2 / 5;
				j_2 = index2 % 5;
				
				if(i_1 == i_2)
				{
					sb.append(charofindex(i_1, (j_1-1+5)%5));
					sb.append(charofindex(i_1, (j_2-1+5)%5));
				}
				else if(j_1 == j_2)
				{
					sb.append(charofindex((i_1-1+5)%5, j_1));
					sb.append(charofindex((i_2-1+5)%5, j_1));
				}
				else
				{
					sb.append(charofindex(i_1, j_2));
					sb.append(charofindex(i_2, j_1));
				}
			}
			s = sb.toString();
			for(i = 0;i < s.length();++i)
			{
				if(s.charAt(i) == 'x')
				{
					if(i > 0 && i < s.length()-1)
					{
						if(s.charAt(i-1) == s.charAt(i+1))
						{
							s = s.replace('x', ' ');
						}
					}
				}
			}
			s = s.replaceAll(" ", "");
			if(s.length()%2 == 0)
			{
				if(s.charAt(s.length()-1) == 'x')
				{
					s = s.replace('x', ' ');
				}
			}
			return s;
		}
	}
	
	static class Affine_cipher//5.仿射密码
	{
		private static char []d = {
				'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
		};
		private int a;
		private int b;
		Affine_cipher(){};
		/***
		 * 构造函数  初始化a，b
		 * @param a
		 * 
		 * @param b
		 * 
		 * **/
		Affine_cipher(int a,int b)
		{
			if(gcd(a, 26) != 1)
			{
				System.out.println("a输入非法");
				return;
			}
			this.a = a;
			this.b = b;
		}
		
		public int Indexofchar(char c)
		{
			int i;
			for(i = 0;i < 26;++i)
			{
				if(c == this.d[i])
				{
					break;
				}
			}
			return i;
		}
		public char Charofindex(int index)
		{
			return this.d[index];
		}
		/**
		 * 求a和b的最大公约数
		 * @param a 参数1
		 * 
		 * @param b 参数2
		 * 
		 * @return 最大公约数
		 * 
		 * **/
		public int gcd(int a, int b)//最大公约数
		{
			if(b == 0)
			{
				return a;
			}
			else
			{
				return gcd(b, a%b);
			}
		}
		/**
		 * 求a的mod26逆元
		 * 
		 * @param a 
		 * 
		 * @param n 被mod数
		 * 
		 * @return 逆元
		 * **/
		public int inver(int a, int n)//逆元
		{
			int i;
			int re;
			for(i = 1;i <= n; ++i)
			{
				re = (a * i) % n;
				if(re == 1)
				{
					break;
				}
			}
			return i;
		}
		/**
		 * 
		 * 加密函数
		 * 
		 * @param str 要加密的字符
		 * 
		 * @return 加密后的字符
		 * 
		 * **/
		public String encode(String str)
		{
			str = str.toLowerCase();
			str = str.replaceAll("[^a-z]", " ");
			StringBuilder sb=new StringBuilder();
			int i;
			int x,e;
			char temp;
			for(i = 0;i < str.length();++i)
			{
				if(str.charAt(i) == ' ')
				{
					sb.append(" ");
				}
				else
				{
					e = (a * Indexofchar(str.charAt(i)) + b) % 26;
					temp = Charofindex(e);
					sb.append(temp);
				}
			}
			return sb.toString();
		}
		/***
		 * 解密函数
		 * 
		 * @param str 要解密的字符串
		 * 
		 * @return 解密后的字符串
		 * 
		 * ***/
		public String decode(String str)
		{
			str = str.toLowerCase();
			str = str.replaceAll("[^a-z]", " ");
			StringBuilder sb=new StringBuilder();
			int i,d;
			char temp;
			for(i = 0;i < str.length();++i)
			{
				if(str.charAt(i) == ' ')
				{
					sb.append(' ');
				}
				else
				{
					d = (inver(a,26) * (Indexofchar(str.charAt(i)) - b)) % 26;
					if(d < 0)d += 26;
					temp = Charofindex(d);
					sb.append(temp);
				}
			}
			return sb.toString();
		}
	}

	static class Hill//6.hill密码
	{
		private int m;
		private int[][] matrix;
		private static char []d = {
				'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
		};
		
		Hill(){};
		/**
		 * 构造函数 初始化矩阵
		 * 
		 * @param n n*n矩阵
		 * 
		 * 
		 * 
		 * ***/
		Hill(int n)
		{
			this.m = n;
			this.matrix = new int[n][n];
			Scanner sc = new Scanner(System.in);
			int i,j;
			System.out.println("请输入"+n+"*"+n+"可逆矩阵！");
			for(i = 0;i < n;++i)
			{
				for(j = 0;j < n;++j)
				{
					matrix[i][j] = sc.nextInt();
				}
			}
			int d = determinant(matrix);
			int flag = 0;
			flag = gcd(d, 26);
			if(d == 0 || flag != 1)//判断是否可逆和行列式与26是否互质
			{
				System.out.println("矩阵不合法！");
				System.exit(0);
			}
		}
		/**
		 * 求a和b的最大公约数
		 * @param a 参数1
		 * 
		 * @param b 参数2
		 * 
		 * @return 最大公约数
		 * 
		 * **/
		public int gcd(int a, int b)//最大公约数
		{
			if(b == 0)
			{
				return a;
			}
			else
			{
				return gcd(b, a%b);
			}
		}
		/**
		 * 取字符所在字符数组d中的下标
		 * 
		 * @param c 要求的字符
		 * 
		 * @return 下标
		 * **/
		public int Indexofchar(char c)
		{
			int i;
			for(i = 0;i < 26;++i)
			{
				if(c == this.d[i])
				{
					break;
				}
			}
			return i;
		}
		/**
		 * 通过下标返回所对的字符
		 * 
		 * @param index 下标
		 * 
		 * @return 所对的字符
		 * **/
		public char Charofindex(int index)
		{
			return this.d[index];
		}
		/***
		 * 
		 * 矩阵乘法result = value * mt
		 * 
		 * @param mt 左乘矩阵
		 * 
		 * @param value 右乘矩阵
		 * 
		 * 
		 * ***/
		public void Computing(int[] value, int[] result,int[][] mt) 
		{
			int i, j;
			for(i = 0;i < this.m;++i)
			{
				for(j = 0;j < this.m;++j)
				{
					result[i] += value[j] * mt[j][i];
				}
				result[i] %= 26;
			}
		}
		/***
		 * 
		 * 求行列式，检验矩阵是否合法
		 * 
		 * @param matrix 要计算的矩阵
		 * 
		 * @return 行列式
		 * ***/
		public int determinant(int[][] matrix)//计算行列式
		{
			if(matrix[0].length == 2)
			{
				return (matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1]);
			}
			else
			{
				int i,j;
				int k,l;
				int c,v;
				c = 0;v = 0;
				int sum = 0;
				int[][] matri = new int[this.m-1][this.m-1];
				for(j = 0,i = 0;j < this.m;++j)//i = 0
				{
					for(k = 0;k < this.m;++k)
					{
						for(l = 0;l < this.m;++l)
						{
							if(k != i && l != j)
							{
								matri[c][v] = matrix[k][l];
								v++;
								if(v >= this.m - 1)
								{
									v = 0;
									c++;
								}
							}
						}
					}
					c = 0;
					sum += (int) Math.pow(-1, i + j) * determinant(matri) * matrix[i][j];
				}
				return sum;
			}
		}
		/***
		 * 
		 * 求行列式，用于求逆矩阵
		 * 
		 * @param matrix 要计算的矩阵
		 * 
		 * @return 行列式
		 * ***/
		public double D(double[][] matrix)//计算行列式
		{
			if(matrix[0].length == 2)
			{
				return (matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1]);
			}
			else
			{
				int i,j;
				int x = matrix.length;
				int y = matrix[0].length;
				double sum = 0;
				double[][] matri = new double[x-1][y-1];
				for(j = 0,i = 0;j < x;++j)//i = 0
				{
					matri = Rase(matrix,i,j);
					sum += (int) Math.pow(-1, i + j) * D(matri) * matrix[i][j];
				}
				return sum;
			}
		}
		/**
		 * 求代数余子式
		 * 
		 * @param matrix 所要求的的矩阵
		 * 
		 * @param index_i 元素的行下标
		 * 
		 * @param index_j 元素的列下标
		 * 
		 * @return 余子式
		 * **/
		
		public double[][] Rase(double[][]matrix,int index_i,int index_j)
		{
			int i,j;
			int k,l;
			int c,v;
			c = 0;v = 0;
			int x = matrix.length;
			int y = matrix[0].length;
			double[][] rasematri = new double[x-1][y-1];
			for(i = 0;i < x;++i)
			{
				for(j = 0;j < y;++j)
				{
					if(i != index_i && j != index_j)
					{
						rasematri[c][v] = matrix[i][j];
						v++;
						if(v >= x - 1)
						{
							v = 0;
							c++;
						}
					}
				}
			}
			return rasematri;
			
		}
		/**
		 * 求逆矩阵（通过求伴随矩阵）
		 * 
		 * @param mt 二维m*m矩阵
		 * 
		 * @return mod26 的伴随矩阵
		 * 
		 * */
		public double[][] Inverse(int[][] mt)//求逆矩阵
		{
			double[][] dm = new double[this.m][this.m];
			double[][] re = new double[this.m][this.m];
			int i,j;
			double d;
			for(i = 0;i < this.m;++i)
			{
				for(j = 0;j < this.m;++j)
				{
					dm[i][j] = (double)mt[i][j];
				}
			}
			if(this.m == 2)//2 * 2的情况
			{
				d = (matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1]);
				d %= 26;
				if(d < 0)d += 26;
				d %= 26;
				d = inver(d,26);//求逆元
				double t1,t2,t3,t4;
				t1 = dm[1][1] *d;//乘逆元
				t2 = -dm[1][0] *d;//乘逆元
				t3 = -dm[0][1] *d;//乘逆元
				t4 = dm[0][0] *d;//乘逆元
				re[0][0] = t1;
				re[0][1] = t2;
				re[1][0] = t3;
				re[1][1] = t4;
			}
			else//m > 2的情况
			{
				d = D(dm);//行列式
				d %= 26;
				if(d < 0)d += 26;
				d %= 26;
				d = inver(d, 26);//求逆元
				for(i = 0;i < this.m;++i)
				{
					for(j = 0;j < this.m;++j)
					{
						if((i+j)%2 == 0) {
		                    re[i][j] = D(Rase(dm, i, j)) * d;//乘逆元
		                }else {
		                    re[i][j] = -D(Rase(dm, i, j)) * d;//乘逆元
		                }
					}
				}
			}
			for(i = 0;i < this.m;++i)//转置
			{
				for(j = 0;j < this.m;++j)
				{
					dm[i][j] = re[j][i];
				}
			}
			for(i = 0;i < this.m;++i)
			{
				for(j = 0;j < this.m;++j)
				{
					dm[i][j] %= 26;
					if(dm[i][j] < 0)dm[i][j] += 26;
					dm[i][j] %= 26;
				}
			}
			return dm;
		}
		/**
		 * 求a的mod26逆元
		 * 
		 * @param a 
		 * 
		 * @param n 被mod数
		 * 
		 * @return 逆元
		 * **/
		public double inver(double a, int n)//逆元
		{
			double i;
			double re;
			for(i = 1;i <= n; ++i)
			{
				re = (a * i) % n;
				if(re == 1)
				{
					break;
				}
			}
			return i;
		}
		/**
		 * 
		 * 加密函数
		 * 
		 * @param str 要加密的字符
		 * 
		 * @return 加密后的字符
		 * 
		 * **/
		public String encode(String str)//加密
		{
			str = str.toLowerCase();
			str = str.replaceAll("[^a-z]", "");
			StringBuilder sb = new StringBuilder(str);
			StringBuilder re = new StringBuilder();
			int d = sb.length()%this.m;
			int i, j;
			if(d != 0)
			{
				for(i = 0;i < this.m - d;++i)
				{
					sb.append(str.charAt(str.length()-1));
				}
			}
			int[] value = new int[this.m];
			int[] result = new int[this.m];
			int k;
			for(i = 0;i < sb.length(); i += this.m)
			{
				k = i;
				for(j = 0;j < this.m;++j)
				{
					value[j] = Indexofchar(sb.charAt(k++));
				}
				Computing(value, result, this.matrix);
				for(j = 0;j < this.m;++j)
				{
					re.append(Charofindex(result[j]));
					result[j] = 0;
				}
			}
			return re.toString();
		}
		/***
		 * 解密函数
		 * 
		 * @param str 要解密的字符串
		 * 
		 * @return 解密后的字符串
		 * 
		 * ***/
		public String decode(String str)//解密
		{
			str = str.toLowerCase();
			str = str.replaceAll("[^a-z]", "");
			StringBuilder sb = new StringBuilder(str);
			StringBuilder re = new StringBuilder();
			double[][] mt = new double[this.m][this.m];
			mt = Inverse(this.matrix);
			int[] value = new int[this.m];
			int[] result = new int[this.m];
			int i, j, k;
			for(i = 0;i < sb.length(); i += this.m)
			{
				k = i;
				for(j = 0;j < this.m;++j)
				{
					value[j] = Indexofchar(sb.charAt(k++));
				}

				for(k = 0;k < this.m;++k)
				{
					for(j = 0;j < this.m;++j)
					{
						result[k] += value[j] * mt[j][k];
					}
					result[k] %= 26;
				}
				for(j = 0;j < this.m;++j)
				{
					re.append(Charofindex(result[j]));
					result[j] = 0;
				}
			}
			return re.toString();
		}
	}

	
}
