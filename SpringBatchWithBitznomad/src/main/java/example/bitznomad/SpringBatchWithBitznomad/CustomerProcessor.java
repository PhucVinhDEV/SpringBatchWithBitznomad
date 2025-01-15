package example.bitznomad.SpringBatchWithBitznomad;

import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer,Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
       //All bussiness logic
        return item;
    }
}
