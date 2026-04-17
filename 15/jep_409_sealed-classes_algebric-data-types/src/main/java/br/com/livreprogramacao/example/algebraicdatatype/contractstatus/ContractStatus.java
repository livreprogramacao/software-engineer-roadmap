package br.com.livreprogramacao.example.algebraicdatatype.contractstatus;

import java.time.LocalDate;
import java.util.UUID;

sealed public interface ContractStatus {
    UUID id();
    String holder();
    String product();

    record Pending(UUID id, String holder, String product, int daysPending)     implements ContractStatus {}
    record Active(UUID id, String holder, String product, LocalDate signedAt)   implements ContractStatus {}
    record Terminated(UUID id, String holder, String product, String reason)    implements ContractStatus {}
}
