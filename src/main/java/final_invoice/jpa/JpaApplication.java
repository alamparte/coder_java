package final_invoice.jpa;

import final_invoice.jpa.repository.ClientRepository;
import final_invoice.jpa.repository.InvoiceDetailsRepository;
import final_invoice.jpa.repository.InvoiceRepository;
import final_invoice.jpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaApplication {
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	InvoiceDetailsRepository invoiceDetailRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	ProductRepository productRepository;
	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

}
