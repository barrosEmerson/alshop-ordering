package com.barrostech.algashop.ordering.domain.valueobject;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    void given_blankDocument_whenTryCreateDocument_shouldGenerateException() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() ->{
                    new Document("");
                });
    }

    @Test
    void given_nullDocument_whenTryCreateDocument_shouldGenerateException() {

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() ->{
                    new Document(null);
                });
    }

    @Test
    void given_validDocument_whenTryCreateDocument_shouldCreateDocument() {

        Document document = new Document("123456789");
        Assertions.assertThat(document.value()).isEqualTo("123456789");
    }

    @Test
    void given_validDocument_when_toString_then_returnDocumentValue() {
        Document document = new Document("123456789");
        Assertions.assertThat(document.toString()).isEqualTo("123456789");
    }

}