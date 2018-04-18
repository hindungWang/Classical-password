package code;

import java.util.Scanner;

public class code {

	public static void main(String[] args) {

		String str;
		System.out.println("����������ܽ��ܣ�");
		Caesars_code c = new Caesars_code();
		str = c.encode("hello world");//�����ַ���
		System.out.println(str);
		System.out.println(c.decode(str));//�����ַ���
		
		System.out.println("��Կ����������ܽ��ܣ�");
		Shortkey_code s = new Shortkey_code("helvsdfd");//���ö���Կ
		str = s.encode("hello world");//����
		System.out.println(str);
		System.out.println(s.decode(str));//����
		
		System.out.println("ά������������ܽ��ܣ�");
		Vigenere_code v = new Vigenere_code("RGDFRDRGD");//������Կ
		str = v.encode("hello world");
		System.out.println(str);//����
		System.out.println(v.decode(str));//����
		
		System.out.println("Playfair������ܽ��ܣ�");
		Playfair_code p = new Playfair_code("ciiiipher");//������Կ
		str = p.encode("hello world");
		System.out.println(str);//����
		System.out.println(p.decode(str));//����
		
		System.out.println("����������ܽ��ܣ�");
		Affine_cipher a = new Affine_cipher(7,3);//����a,bֵ
		str = a.encode("hello world");
		System.out.println(str);//����
		System.out.println(a.decode(str));//����
		
		System.out.println("Hill������ܽ��ܣ�");
		Hill h = new Hill(3);//���÷��������������ַ��ķ��鳤�ȣ�
		str = h.encode("hello world");//����
		System.out.println(str);
		System.out.println(h.decode(str));//����
	}
	static class Caesars_code//1.��������ʵ��
	{
		private String p = "abcdefghijklmnopqrstuvwxyz";//����
		private String e = "defghijklmnopqrstuvwxyzabc";//����
		
		 /*** 
	     * ����������� 
	     *  
	     * @param str ��Ҫ���ܵ��ַ���
	     *            
	     * @return ���ܵĽ��
	     */  
		public String encode(String str)//����
		{
			String s = new String();
			str = str.toLowerCase();//ת��ΪСд��ĸ
			str = str.replaceAll("[^a-z]", "");//���˷���ĸ�ַ�
			int i, j;
			char peek;
			for(i = 0;i < str.length();++i)//����ֱ��һһ��Ӧ��ʽ
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
	     * ����������� 
	     *  
	     * @param str ��Ҫ���ܵ��ַ���
	     *            
	     * @return ���ܵĽ��
	     */  
		public String decode(String str)//����
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
	
	static class Shortkey_code//2.��Կ��������
	{
		private String shortkey;//����Կ
		private String p = "abcdefghijklmnopqrstuvwxyz";//����
		private String e;//����������
		
		Shortkey_code(){};
		/***
		 * ���캯������ʼ������Կ�ͼ�������
		 * 
		 * @param s ����Կ
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
			//��������Կ����Ϸ��Լ��  ȥ�ظ����ַ�
			for(i = 0;i < len;++i)
			{
				peek = i+1;
				while(peek < len - count)
				{
					if(c[i] == c[peek])
					{
						for(j = peek;j < len - 1;++j)
						{
							c[j] = c[j + 1];//�����ظ���ĸ����peekλ�ÿ�ʼ��������ǰŲλ
						}
						count++;//�ظ���ĸ���ֵĴ���
						peek--;
					}
					peek++;
				}
			}
			for(i = 0;i < len - count;++i)
			{
				sk += String.valueOf(c[i]);
			}
			this.shortkey = sk;//��ʼ����Կ����
			this.e += sk;//��ʼ������
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
		
		public void printEs()//��ӡ���ĺͶ���Կ
		{
			System.out.println(this.shortkey);
			System.out.println(this.e);
		}
		/*** 
	     * ����Կ������� 
	     *  
	     * @param str ��Ҫ���ܵ��ַ���
	     *            
	     * @return ���ܵĽ��
	     */
		public String encode(String str)//����
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
	     * ����Կ������� 
	     *  
	     * @param str ��Ҫ���ܵ��ַ���
	     *            
	     * @return ���ܵĽ��
	     */
		public String decode(String str)//����
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

	static class Vigenere_code//3.ά����������
	{
		private static char []d = {
				'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
		};
		private char [][] dir = new char[26][26];
		private String key;
		
		Vigenere_code() {};
		/**
		 * ���캯��  ��ʼ����Կ
		 * 
		 * @param str ��Կ�ַ�
		 * 
		 * 
		 * ***/
		
		Vigenere_code(String str)
		{
			str = str.toLowerCase();
			this.key = str;
			int i, j;
			int flag = 0;
			for(i = 0;i < 26;++i)//��ʼ����ά��
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
		 * ȡ��ǰ�ַ������ַ�����d�е��±�
		 * 
		 * @param c �ַ�
		 * 
		 * @return �±�
		 * 
		 * **/
		public int Indexof(char c)//ȡ�±�
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
		 * ���ܺ���
		 * 
		 * @param str Ҫ���ܵ��ַ�
		 * 
		 * @return ���ܺ���ַ�
		 * 
		 * **/
		public String encode(String str)
		{
			str = str.toLowerCase();
			String re = new String();
			int lenstr = str.length();
			int lenkey = this.key.length();//Ĭ����Կ����С�����ĳ���
			
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
		 * ���ܺ���
		 * 
		 * @param str Ҫ���ܵ��ַ���
		 * 
		 * @return ���ܺ���ַ���
		 * 
		 * ***/
		public String decode(String str)
		{
			str = str.toLowerCase();
			String re = new String();
			int lenstr = str.length();
			int lenkey = this.key.length();//Ĭ����Կ����С�����ĳ���
			
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

	static class Playfair_code//4.����ĸ�滻����
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
			//��������Կ����Ϸ��Լ��
			for(i = 0;i < len;++i)
			{
				peek = i+1;
				while(peek < len - count)
				{
					if(c[i] == c[peek])
					{
						for(j = peek;j < len - 1;++j)
						{
							c[j] = c[j + 1];//�����ظ���ĸ����peekλ�ÿ�ʼ��������ǰŲλ
						}
						count++;//�ظ���ĸ���ֵĴ���
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
		 * �ж��ַ��Ƿ���str��
		 * 
		 * @param c ���жϵ��ַ�
		 * 
		 * @param str �Աȵ��ַ���
		 * 
		 * @return �Ƿ����ַ�����
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
		 * ���ܺ���
		 * 
		 * @param s Ҫ���ܵ��ַ�
		 * 
		 * @return ���ܺ���ַ�
		 * 
		 * **/
		public String encode(String s)//����
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
				if(i_1 == i_2)//ͬ������
				{
					sb.append(charofindex(i_1, (j_1+1)%5));
					sb.append(charofindex(i_1, (j_2+1)%5));
				}
				else if(j_1 == j_2)//ͬ������
				{
					sb.append(charofindex((i_1+1)%5, j_1));
					sb.append(charofindex((i_2+1)%5, j_1));
				}
				else
				{
					sb.append(charofindex(i_1, j_2));//�Խ���
					sb.append(charofindex(i_2, j_1));
				}
				sb.append(' ');
			}
			return sb.toString();
		}
		/***
		 * ���ܺ���
		 * 
		 * @param s Ҫ���ܵ��ַ���
		 * 
		 * @return ���ܺ���ַ���
		 * 
		 * ***/
		public String decode(String s)//����
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
	
	static class Affine_cipher//5.��������
	{
		private static char []d = {
				'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
		};
		private int a;
		private int b;
		Affine_cipher(){};
		/***
		 * ���캯��  ��ʼ��a��b
		 * @param a
		 * 
		 * @param b
		 * 
		 * **/
		Affine_cipher(int a,int b)
		{
			if(gcd(a, 26) != 1)
			{
				System.out.println("a����Ƿ�");
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
		 * ��a��b�����Լ��
		 * @param a ����1
		 * 
		 * @param b ����2
		 * 
		 * @return ���Լ��
		 * 
		 * **/
		public int gcd(int a, int b)//���Լ��
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
		 * ��a��mod26��Ԫ
		 * 
		 * @param a 
		 * 
		 * @param n ��mod��
		 * 
		 * @return ��Ԫ
		 * **/
		public int inver(int a, int n)//��Ԫ
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
		 * ���ܺ���
		 * 
		 * @param str Ҫ���ܵ��ַ�
		 * 
		 * @return ���ܺ���ַ�
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
		 * ���ܺ���
		 * 
		 * @param str Ҫ���ܵ��ַ���
		 * 
		 * @return ���ܺ���ַ���
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

	static class Hill//6.hill����
	{
		private int m;
		private int[][] matrix;
		private static char []d = {
				'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
		};
		
		Hill(){};
		/**
		 * ���캯�� ��ʼ������
		 * 
		 * @param n n*n����
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
			System.out.println("������"+n+"*"+n+"�������");
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
			if(d == 0 || flag != 1)//�ж��Ƿ���������ʽ��26�Ƿ���
			{
				System.out.println("���󲻺Ϸ���");
				System.exit(0);
			}
		}
		/**
		 * ��a��b�����Լ��
		 * @param a ����1
		 * 
		 * @param b ����2
		 * 
		 * @return ���Լ��
		 * 
		 * **/
		public int gcd(int a, int b)//���Լ��
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
		 * ȡ�ַ������ַ�����d�е��±�
		 * 
		 * @param c Ҫ����ַ�
		 * 
		 * @return �±�
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
		 * ͨ���±귵�����Ե��ַ�
		 * 
		 * @param index �±�
		 * 
		 * @return ���Ե��ַ�
		 * **/
		public char Charofindex(int index)
		{
			return this.d[index];
		}
		/***
		 * 
		 * ����˷�result = value * mt
		 * 
		 * @param mt ��˾���
		 * 
		 * @param value �ҳ˾���
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
		 * ������ʽ����������Ƿ�Ϸ�
		 * 
		 * @param matrix Ҫ����ľ���
		 * 
		 * @return ����ʽ
		 * ***/
		public int determinant(int[][] matrix)//��������ʽ
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
		 * ������ʽ�������������
		 * 
		 * @param matrix Ҫ����ľ���
		 * 
		 * @return ����ʽ
		 * ***/
		public double D(double[][] matrix)//��������ʽ
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
		 * ���������ʽ
		 * 
		 * @param matrix ��Ҫ��ĵľ���
		 * 
		 * @param index_i Ԫ�ص����±�
		 * 
		 * @param index_j Ԫ�ص����±�
		 * 
		 * @return ����ʽ
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
		 * �������ͨ����������
		 * 
		 * @param mt ��άm*m����
		 * 
		 * @return mod26 �İ������
		 * 
		 * */
		public double[][] Inverse(int[][] mt)//�������
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
			if(this.m == 2)//2 * 2�����
			{
				d = (matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1]);
				d %= 26;
				if(d < 0)d += 26;
				d %= 26;
				d = inver(d,26);//����Ԫ
				double t1,t2,t3,t4;
				t1 = dm[1][1] *d;//����Ԫ
				t2 = -dm[1][0] *d;//����Ԫ
				t3 = -dm[0][1] *d;//����Ԫ
				t4 = dm[0][0] *d;//����Ԫ
				re[0][0] = t1;
				re[0][1] = t2;
				re[1][0] = t3;
				re[1][1] = t4;
			}
			else//m > 2�����
			{
				d = D(dm);//����ʽ
				d %= 26;
				if(d < 0)d += 26;
				d %= 26;
				d = inver(d, 26);//����Ԫ
				for(i = 0;i < this.m;++i)
				{
					for(j = 0;j < this.m;++j)
					{
						if((i+j)%2 == 0) {
		                    re[i][j] = D(Rase(dm, i, j)) * d;//����Ԫ
		                }else {
		                    re[i][j] = -D(Rase(dm, i, j)) * d;//����Ԫ
		                }
					}
				}
			}
			for(i = 0;i < this.m;++i)//ת��
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
		 * ��a��mod26��Ԫ
		 * 
		 * @param a 
		 * 
		 * @param n ��mod��
		 * 
		 * @return ��Ԫ
		 * **/
		public double inver(double a, int n)//��Ԫ
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
		 * ���ܺ���
		 * 
		 * @param str Ҫ���ܵ��ַ�
		 * 
		 * @return ���ܺ���ַ�
		 * 
		 * **/
		public String encode(String str)//����
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
		 * ���ܺ���
		 * 
		 * @param str Ҫ���ܵ��ַ���
		 * 
		 * @return ���ܺ���ַ���
		 * 
		 * ***/
		public String decode(String str)//����
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
