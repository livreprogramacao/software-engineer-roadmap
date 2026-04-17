import br.com.livreprogramacao.example.algebraicdatatype.contractstatus.ContractStatus;

public void handleContractStatus(ContractStatus status) {

    switch (status) {
        case ContractStatus.Pending p -> sendNotification(p.id(), p.holder(), p.daysPending());
        case ContractStatus.Active a -> renewContratct(a.id(), a.holder(), a.product(), a.signedAt());
        case ContractStatus.Terminated t -> retargetProspect(t.holder(), t.product(), t.reason());
    }

}