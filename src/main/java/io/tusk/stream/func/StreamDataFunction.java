package io.tusk.stream.func;

import io.tusk.stream.Column;
import io.tusk.stream.StreamData;
import io.tusk.stream.impl.StreamDataImpl;

import java.util.List;

import cascading.flow.FlowProcess;
import cascading.operation.FunctionCall;
import cascading.tuple.Fields;
import cascading.tuple.TupleEntry;
import cascading.tuple.TupleEntryCollector;

public class StreamDataFunction<Context> extends DataFunction<Context, StreamData, StreamData> {
    @Override
    public void operate(FlowProcess flowProcess, FunctionCall<Context> functionCall) {
        Fields fields = functionCall.getDeclaredFields();
        TupleEntry entry = functionCall.getArguments();

        StreamDataImpl data = new StreamDataImpl(entry); 
        
        StreamData output = getFunction().apply(data);
        
        TupleEntry tuple = new TupleEntry();

        List<Column> cols = output.getColumns();

        for (Column col : cols) {
            tuple.setRaw(col.getName(), col.getData());
        }


        TupleEntryCollector collector = functionCall.getOutputCollector();
        collector.setFields(tuple.getFields());
        collector.add(tuple.getTuple());

    }
}
