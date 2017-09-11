package netty.tianjian.common.util.fork;


import netty.tianjian.common.util.http.HtmlContent;
import netty.tianjian.common.util.http.HttpFileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/7
 * @description
 */
public class ForkJoinCalculator extends RecursiveTask<Set<String>> {
    //要求和的数组
    private final Set<String> urls;
    //子任务处理的数组开始和终止的位置
    private final int start;
    private final int end;
    //不在将任务分解成子任务的阀值大小
    public static final int THRESHOLD = 300;


    //用于创建组任务的构造函数
    public ForkJoinCalculator(Set<String> numbers){
        this(numbers, 0, numbers.size());
    }

    //用于递归创建子任务的构造函数
    public ForkJoinCalculator(Set<String> urls, int start, int end){
        this.urls = urls;
        this.start = start;
        this.end = end;
    }

    //重写接口的方法
    @Override
    protected Set<String> compute() {
        //当前任务负责求和的部分的大小
        int length = end - start;
        //如果小于等于阀值就顺序执行计算结果
        if(length <= THRESHOLD){
            try {
                return computeSequentially();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //创建子任务来为数组的前一半求和
        ForkJoinCalculator leftTask = new ForkJoinCalculator(urls, start, start + length/2);
        //将子任务拆分出去，丢到ForkJoinPool线程池异步执行。
        leftTask.fork();
        //创建子任务来为数组的后一半求和
        ForkJoinCalculator rightTask = new ForkJoinCalculator(urls, start + length/2, end);
        //第二个任务直接使用当前线程计算而不再开启新的线程。
        Set<String> rightResult = rightTask.compute();
        //读取第一个子任务的结果，如果没有完成则等待。
        Set<String> leftResult = leftTask.join();
        //合并两个子任务的计算结果
        rightResult.addAll(rightResult);
        return rightResult;
    }

    //http://www.cnblogs.com/MikeZhang/p/pythonProfile20170907.html#commentform
    //顺序执行计算的简单算法
    private Set<String> computeSequentially() throws IOException {
        Set<String> waitUrls = new HashSet<String>();
        for(String url : urls) {
            if(url.equals("http://www.cnblogs.com/MikeZhang/p/pythonProfile20170907.html#commentform")) {
                System.out.println("========================================================");
            }
            HtmlContent contents = new HtmlContent(url);
            contents.saveElasticData();
            HttpFileUtil.saveStringToFile(contents.getContent(), contents.getRoot(), contents.getTitle());
            Set<String> linkeds = contents.getLinked();
            for(String linked : linkeds ) {
                waitUrls.add(linked);
            }
        }
       return waitUrls;
    }
    //提供给外部使用的入口方法S
    public static Set<String> forkJoinSum(Set<String> urls) {
        ForkJoinTask<Set<String>> task = new ForkJoinCalculator(urls);
        return new ForkJoinPool().invoke(task);
    }
}