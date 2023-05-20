package com.findservices.serviceprovider.servicerequest.model;

public enum RequestStatusType {

    /**
     * Pendente para o prestador de serviço
     */
    PENDING_SERVICE_ACCEPT,

    /**
     * Pendente para o cliente aprovar o orçamento
     */
    PENDING_CLIENT_APPROVED,

    /**
     * Aprovado por ambas as partes
     */
    APPROVED,

    /**
     * Pedido cancelado pelo cliente
     */
    CANCELED,

    /**
     * Serviço rejeitado pelo prestador de serviço
     */
    SERVICE_REJECTED,

    /**
     * Concluido
     */
    DONE

}
