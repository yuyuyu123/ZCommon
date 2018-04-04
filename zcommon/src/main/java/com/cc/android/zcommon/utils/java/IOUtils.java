package com.cc.android.zcommon.utils.java;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * <b>I/O工具类，提供一些有关I/O流的便捷方法</b>
 * <br>
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;从给定的字节输入流中读取字节：public static byte[] read(InputStream input, long off, int length)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;从给定的字节输入流中读取字节：public static byte[] read(InputStream input, long off)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;从给定的字节输入流中读取字节：public static byte[] read(InputStream input, int length)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;从给定的字节输入流中读取字节：public static byte[] read(InputStream input)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;从给定的字符输入流中读取字符：public static char[] read(Reader reader, long off, int length)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;从给定的字符输入流中读取字符：public static char[] read(Reader reader, long off)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;从给定的字符输入流中读取字符：public static char[] read(Reader reader, int length)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;从给定的字符输入流中读取字符：public static char[] read(Reader reader)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;通过给定的字节输出流写出给定的字节数组：public static void write(OutputStream output, byte[] bytes, long off, int length)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;通过给定的字节输出流写出给定的字节数组：public static void write(OutputStream output, byte[] bytes, long off)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;通过给定的字节输出流写出给定的字节数组：public static void write(OutputStream output, byte[] bytes, int length)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;通过给定的字节输出流写出给定的字节数组：public static void write(OutputStream output, byte[] bytes)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;通过给定的字符输出流写出给定的字符数组：public static void write(Writer writer, char[] chars, long off, int length)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;通过给定的字符输出流写出给定的字符数组：public static void write(Writer writer, char[] chars, long off)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;通过给定的字符输出流写出给定的字符数组：public static void write(Writer writer, char[] chars, int length)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;通过给定的字符输出流写出给定的字符数组：public static void write(Writer writer, char[] chars)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;从给定的字节输入流中读取字节再通过给定的字节输出流写出：public static void outputFromInput(InputStream input, OutputStream output)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;从给定的字符输入流中读取字符再通过给定的字符输出流写出：public static void writerFromReader(Reader reader, Writer writer)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;打开一个字节输入流：public static InputStream openInputStream(File file)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;打开一个加了缓冲区的字节输入流：public static BufferedInputStream openBufferedInputStream(File file)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;打开一个字符输入流：public static Reader openReader(File file)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;打开一个使用给定的字符集编码的字符输入流：public static Reader openReader(File file, Charset charset)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;打开一个加了缓冲区的字符输入流：public static BufferedReader openBufferedReader(File file)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;打开一个使用给定的字符集编码并且加了缓冲区的字符输入流：public static BufferedReader openBufferedReader(File file, Charset charset)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;打开一个字节输出流：public static OutputStream openOutputStream(File file, boolean isAppend)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;打开一个加了缓冲区的字节输出流：public static BufferedOutputStream openBufferedOutputStream(File file, boolean isAppend)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;打开一个字符输出流：public static Writer openWriter(File file, boolean isAppend)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;打开一个使用给定的字符集编码的字符输出流：public static Writer openWriter(File file, boolean isAppend, Charset charset)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;打开一个加了缓冲区的字符输出流：public static BufferedWriter openBufferedWriter(File file, boolean isAppend)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;打开一个使用给定的字符集编码并且加了缓冲区的字符输出流：public static BufferedWriter openBufferedWriter(File file, boolean isAppend, Charset charset)
 * <br>
 */
public class IOUtils {

	/**
	 * 每次最多读取的长度
	 */
	public static final int MAX_OPERATION_LENGTH = 1024;

	/**
	 * 从给定的字节输入流中读取字节
	 * @param input 给定的字节输入流
	 * @param off 偏移量，从此处开始读取数据
	 * @param length 要读取的数据的长度
	 * @return 一个字节数组，其长度可能小于length
	 * @throws IOException
	 */
	public static byte[] read(InputStream input, long off, int length) throws IOException {
		byte[] result;
		input.skip(off);
		byte[] bytes = new byte[length];
		int number = input.read(bytes);
		if(number == length){
			result = bytes;
		}else{
			result = new byte[number];
			System.arraycopy(bytes, 0, result, 0, number);
		}
		return result;
	}

	/**
	 * 从给定的字节输入流中读取字节
	 * @param input 给定的字节输入流
	 * @param off 偏移量，从此处开始读取数据
	 * @return 一个字节数组
	 * @throws IOException
	 */
	public static byte[] read(InputStream input, long off) throws IOException {
		input.skip(off);
		byte[] bytes = new byte[MAX_OPERATION_LENGTH];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int number = -1;
		while((number = input.read(bytes)) != -1){
			baos.write(bytes, 0, number);
		}
		return baos.toByteArray();
	}

	/**
	 * 从给定的字节输入流中读取字节
	 * @param input 给定的字节输入流
	 * @param length 要读取的数据的长度
	 * @return 一个字节数组，其长度可能小于length
	 * @throws IOException
	 */
	public static byte[] read(InputStream input, int length) throws IOException {
		byte[] result;
		byte[] bytes = new byte[length];
		int number = input.read(bytes);
		if(number == length){
			result = bytes;
		}else{
			result = new byte[number];
			System.arraycopy(bytes, 0, result, 0, number);
		}
		return result;
	}

	/**
	 * 从给定的字节输入流中读取字节
	 * @param input 给定的字节输入流
	 * @return 全部数据
	 * @throws IOException
	 */
	public static byte[] read(InputStream input) throws IOException {
		byte[] bytes = new byte[MAX_OPERATION_LENGTH];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int number = -1;
		while((number = input.read(bytes)) != -1){
			baos.write(bytes, 0, number);
		}
		return baos.toByteArray();
	}

	/**
	 * 从给定的字符输入流中读取字符
	 * @param reader 给定的字符输入流
	 * @param off 偏移量，从此处开始读取数据
	 * @param length 要读取的数据的长度
	 * @return 一个字符数组，其长度可能小于length
	 * @throws IOException
	 */
	public static char[] read(Reader reader, long off, int length) throws IOException {
		char[] result;
		reader.skip(off);
		char[] chars = new char[length];
		int number = reader.read(chars);
		if(number == length){
			result = chars;
		}else{
			result = new char[number];
			System.arraycopy(chars, 0, result, 0, number);
		}
		return result;
	}

	/**
	 * 从给定的字符输入流中读取字符
	 * @param reader 给定的字符输入流
	 * @param off 偏移量，从此处开始读取数据
	 * @return 一个字符数组，其长度可能小于length
	 * @throws IOException
	 */
	public static char[] read(Reader reader, long off) throws IOException {
		reader.skip(off);
		char[] chars = new char[MAX_OPERATION_LENGTH];
		CharArrayWriter caw = new CharArrayWriter();
		int number = -1;
		while((number = reader.read(chars)) != -1){
			caw.write(chars, 0, number);
		}
		return caw.toCharArray();
	}

	/**
	 * 从给定的字符输入流中读取字符
	 * @param reader 给定的字符输入流
	 * @param length 要读取的数据的长度
	 * @return 一个字符数组，其长度可能小于length
	 * @throws IOException
	 */
	public static char[] read(Reader reader, int length) throws IOException {
		char[] result;
		char[] chars = new char[length];
		int number = reader.read(chars);
		if(number == length){
			result = chars;
		}else{
			result = new char[number];
			System.arraycopy(chars, 0, result, 0, number);
		}
		return result;
	}

	/**
	 * 从给定的字符输入流中读取字符
	 * @param reader 给定的字符输入流
	 * @return 全部数据
	 * @throws IOException
	 */
	public static char[] read(Reader reader) throws IOException {
		char[] chars = new char[MAX_OPERATION_LENGTH];
		CharArrayWriter caw = new CharArrayWriter();
		int number = -1;
		while((number = reader.read(chars)) != -1){
			caw.write(chars, 0, number);
		}
		return caw.toCharArray();
	}

	/**
	 * 通过给定的字节输出流写出给定的字节数组
	 * @param output 给定的字节输出流
	 * @param bytes 给定的字节数组
	 * @param off 偏移量，从字节数组的此处开始获取数据写到输出流中去
	 * @param length 要写出的数据的长度
	 * @throws IOException
	 */
	public static void write(OutputStream output, byte[] bytes, long off, int length) throws IOException {
		output.write(bytes, (int)off, length);
		output.flush();
	}

	/**
	 * 通过给定的字节输出流写出给定的字节数组
	 * @param output 给定的字节输出流
	 * @param bytes 给定的字节数组
	 * @param off 偏移量，从字节数组的此处开始获取数据写到输出流中去
	 * @throws IOException
	 */
	public static void write(OutputStream output, byte[] bytes, long off) throws IOException {
		output.write(bytes, (int)off, bytes.length - (int)off);
		output.flush();
	}

	/**
	 * 通过给定的字节输出流写出给定的字节数组
	 * @param output 给定的字节输出流
	 * @param bytes 给定的字节数组
	 * @param length 要写出的数据的长度
	 * @throws IOException
	 */
	public static void write(OutputStream output, byte[] bytes, int length) throws IOException {
		output.write(bytes, 0, length);
		output.flush();
	}

	/**
	 * 通过给定的字节输出流写出给定的字节数组
	 * @param output 给定的字节输出流
	 * @param bytes 给定的字节数组
	 * @throws IOException
	 */
	public static void write(OutputStream output, byte[] bytes) throws IOException {
		output.write(bytes);
		output.flush();
	}

	/**
	 * 通过给定的字符输出流写出给定的字符数组
	 * @param writer 给定的字符输出流
	 * @param chars 给定的字符数组
	 * @param off 偏移量，从字符数组的此处开始获取数据写到输出流中去
	 * @param length 要写出的数据的长度
	 * @throws IOException
	 */
	public static void write(Writer writer, char[] chars, long off, int length) throws IOException {
		writer.write(chars, (int)off, length);
		writer.flush();
	}

	/**
	 * 通过给定的字符输出流写出给定的字符数组
	 * @param writer 给定的字符输出流
	 * @param chars 给定的字符数组
	 * @param off 偏移量，从字符数组的此处开始获取数据写到输出流中去
	 * @throws IOException
	 */
	public static void write(Writer writer, char[] chars, long off) throws IOException {
		writer.write(chars, (int)off, chars.length - (int)off);
		writer.flush();
	}

	/**
	 * 通过给定的字符输出流写出给定的字符数组
	 * @param writer 给定的字符输出流
	 * @param chars 给定的字符数组
	 * @param length 要写出的数据的长度
	 * @throws IOException
	 */
	public static void write(Writer writer, char[] chars, int length) throws IOException {
		writer.write(chars, 0, length);
		writer.flush();
	}

	/**
	 * 通过给定的字符输出流写出给定的字符数组
	 * @param writer 给定的字符输出流
	 * @param chars 给定的字符数组
	 * @throws IOException
	 */
	public static void write(Writer writer, char[] chars) throws IOException {
		writer.write(chars);
		writer.flush();
	}

	/**
	 * 从给定的字节输入流中读取字节再通过给定的字节输出流写出
	 * @param input 给定的字节输入流
	 * @param output 给定的字节输出流
	 * @throws IOException
	 */
	public static void outputFromInput(InputStream input, OutputStream output) throws IOException {
		byte[] bytes = new byte[MAX_OPERATION_LENGTH];
		int number;
		while((number = input.read(bytes)) != -1){
			output.write(bytes, 0, number);
		}
		output.flush();
	}

	/**
	 * 从给定的字符输入流中读取字符再通过给定的字符输出流写出
	 * @param reader 给定的字符输入流
	 * @param writer 给定的字符输出流
	 * @throws IOException
	 */
	public static void writerFromReader(Reader reader, Writer writer) throws IOException {
		char[] chars = new char[MAX_OPERATION_LENGTH];
		int number;
		while((number = reader.read(chars)) != -1){
			writer.write(chars, 0, number);
		}
		writer.flush();
	}

	/**
	 * 打开一个字节输入流
	 * @param file 源文件
	 * @return 字节输入流
	 * @throws FileNotFoundException
	 */
	public static InputStream openInputStream(File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}

	/**
	 * 打开一个加了缓冲区的字节输入流
	 * @param file 源文件
	 * @return 加了缓冲区的字节输入流
	 * @throws FileNotFoundException
	 */
	public static BufferedInputStream openBufferedInputStream(File file) throws FileNotFoundException {
		return new BufferedInputStream(openInputStream(file));
	}

	/**
	 * 打开一个字符输入流
	 * @param file 源文件
	 * @return 字符输入流
	 * @throws FileNotFoundException
	 */
	public static Reader openReader(File file) throws FileNotFoundException {
		return new FileReader(file);
	}

	/**
	 * 打开一个使用给定的字符集编码的字符输入流
	 * @param file 源文件
	 * @param charset 给定的字符集
	 * @return 字符输入流
	 * @throws FileNotFoundException
	 */
	public static Reader openReader(File file, Charset charset) throws FileNotFoundException {
		return new InputStreamReader(openInputStream(file), charset);
	}

	/**
	 * 打开一个加了缓冲区的字符输入流
	 * @param file 源文件
	 * @return 加了缓冲区的字符输入流
	 * @throws FileNotFoundException
	 */
	public static BufferedReader openBufferedReader(File file) throws FileNotFoundException {
		return new BufferedReader(openReader(file));
	}

	/**
	 * 打开一个使用给定的字符集编码并且加了缓冲区的字符输入流
	 * @param file 源文件
	 * @param charset 给定的字符集
	 * @return 加了缓冲区的字符输入流
	 * @throws FileNotFoundException
	 */
	public static BufferedReader openBufferedReader(File file, Charset charset) throws FileNotFoundException {
		return new BufferedReader(openReader(file, charset));
	}

	/**
	 * 打开一个字节输出流
	 * @param file 源文件
	 * @param isAppend 是否追加到文件末尾
	 * @return 字节输出流
	 * @throws FileNotFoundException
	 */
	public static OutputStream openOutputStream(File file, boolean isAppend) throws FileNotFoundException {
		return new FileOutputStream(file, isAppend);
	}

	/**
	 * 打开一个加了缓冲区的字节输出流
	 * @param file 源文件
	 * @param isAppend 是否追加到文件末尾
	 * @return 加了缓冲区的字节输出流
	 * @throws FileNotFoundException
	 */
	public static BufferedOutputStream openBufferedOutputStream(File file, boolean isAppend) throws FileNotFoundException {
		return new BufferedOutputStream(openOutputStream(file, isAppend));
	}

	/**
	 * 打开一个字符输出流
	 * @param file 源文件
	 * @param isAppend 是否追加到文件末尾
	 * @return 字符输出流
	 * @throws IOException
	 */
	public static Writer openWriter(File file, boolean isAppend) throws IOException {
		return new FileWriter(file, isAppend);
	}

	/**
	 * 打开一个使用给定的字符集编码的字符输出流
	 * @param file 源文件
	 * @param charset 给定的字符集
	 * @param isAppend 是否追加到文件末尾
	 * @return 使用给定的字符集编码的字符输出流
	 * @throws IOException
	 */
	public static Writer openWriter(File file, Charset charset, boolean isAppend) throws FileNotFoundException {
		return new OutputStreamWriter(openOutputStream(file, isAppend), charset);
	}

	/**
	 * 打开一个加了缓冲区的字符输出流
	 * @param file 源文件
	 * @param isAppend 是否追加到文件末尾
	 * @return 加了缓冲区的字符输出流
	 * @throws FileNotFoundException
	 */
	public static BufferedWriter openBufferedWriter(File file, boolean isAppend) throws IOException {
		return new BufferedWriter(openWriter(file, isAppend));
	}

	/**
	 * 打开一个使用给定的字符集编码并且加了缓冲区的字符输出流
	 * @param file 源文件
	 * @param charset 给定的字符集
	 * @param isAppend 是否追加到文件末尾
	 * @return 使用给定的字符集编码并且加了缓冲区的字符输出流
	 * @throws IOException
	 */
	public static BufferedWriter openBufferedWriter(File file, Charset charset, boolean isAppend) throws FileNotFoundException {
		return new BufferedWriter(openWriter(file, charset, isAppend));
	}
}
