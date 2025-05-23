package com.l2.packing_service;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


@SpringBootTest
@AutoConfigureMockMvc
class PackingControllerValidationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;


    @Test
    void shouldFailNotAuth() throws Exception {
        var body = Map.of(
                "pedidos", List.of(
                        Map.of(
                                "pedido_id", 1,
                                "produtos", List.of(
                                        Map.of("produto_id", "S1",
                                                "dimensoes", Map.of("altura", 10, "largura", 10, "comprimento", 10)),
                                        Map.of("produto_id", "S2",
                                                "dimensoes", Map.of("altura", 10, "largura", 10, "comprimento", 10)),
                                        Map.of("produto_id", "M1",
                                                "dimensoes", Map.of("altura", 40, "largura", 40, "comprimento", 40)),
                                        Map.of("produto_id", "L1",
                                                "dimensoes", Map.of("altura", 50, "largura", 70, "comprimento", 60))
                                )
                        )
                )
        );

        mvc.perform(post("/api/v1/packing")
                        .with(httpBasic("admin", "secret1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(body)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldUseSmallMediumAndLargeBoxes() throws Exception {
        var body = Map.of(
                "pedidos", List.of(
                        Map.of(
                                "pedido_id", 1,
                                "produtos", List.of(
                                        Map.of("produto_id", "S1",
                                                "dimensoes", Map.of("altura", 10, "largura", 10, "comprimento", 10)),
                                        Map.of("produto_id", "S2",
                                                "dimensoes", Map.of("altura", 10, "largura", 10, "comprimento", 10)),
                                        Map.of("produto_id", "M1",
                                                "dimensoes", Map.of("altura", 40, "largura", 40, "comprimento", 40)),
                                        Map.of("produto_id", "L1",
                                                "dimensoes", Map.of("altura", 50, "largura", 70, "comprimento", 60))
                                )
                        )
                )
        );

        mvc.perform(post("/api/v1/packing")
                        .with(httpBasic("admin", "secret"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidos[0].caixas", hasSize(3)))
                .andExpect(jsonPath("$.pedidos[0].caixas[?(@.caixa_id == 'Caixa 1')]").isNotEmpty())
                .andExpect(jsonPath("$.pedidos[0].caixas[?(@.caixa_id == 'Caixa 2')]").isNotEmpty())
                .andExpect(jsonPath("$.pedidos[0].caixas[?(@.caixa_id == 'Caixa 3')]").isNotEmpty())
                .andExpect(jsonPath("$.pedidos[0].caixas[?(@.observacao != null)]").isEmpty());
    }

    @Test
    void shouldUseSmallMediumAndLargeBoxesAndItemsNotFit() throws Exception {
        var body = Map.of(
                "pedidos", List.of(
                        Map.of(
                                "pedido_id", 1,
                                "produtos", List.of(
                                        Map.of("produto_id", "1",
                                                "dimensoes", Map.of("altura", 10, "largura", 10, "comprimento", 10)),
                                        Map.of("produto_id", "2",
                                                "dimensoes", Map.of("altura", 10, "largura", 10, "comprimento", 10)),
                                        Map.of("produto_id", "3",
                                                "dimensoes", Map.of("altura", 40, "largura", 40, "comprimento", 40)),
                                        Map.of("produto_id", "4",
                                                "dimensoes", Map.of("altura", 50, "largura", 70, "comprimento", 60)),
                                        Map.of("produto_id", "5",
                                                "dimensoes", Map.of("altura", 40, "largura", 40, "comprimento", 40)),
                                        Map.of("produto_id", "6",
                                                "dimensoes", Map.of("altura", 40, "largura", 40, "comprimento", 40))
                                )
                        )
                )
        );

        mvc.perform(post("/api/v1/packing")
                        .with(httpBasic("admin", "secret"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidos[0].caixas", hasSize(4)))
                .andExpect(jsonPath("$.pedidos[0].caixas[?(@.caixa_id == 'Caixa 1')]").isNotEmpty())
                .andExpect(jsonPath("$.pedidos[0].caixas[?(@.caixa_id == 'Caixa 2')]").isNotEmpty())
                .andExpect(jsonPath("$.pedidos[0].caixas[?(@.caixa_id == 'Caixa 3')]").isNotEmpty())
                .andExpect(jsonPath("$.pedidos[0].caixas[*].observacao", hasItem("Produto não cabe em nenhuma caixa disponível.")));

    }

    @Test
    void shouldReturn400WhenFieldsAreInvalidItem1() throws Exception {
        var badBody = Map.of("pedidos", List.of(Map.of("pedido_id", 1, "produtos", List.of(Map.of("produto_id", "", "dimensoes", Map.of("altura", 0, "largura", 0, "comprimento", 0))))));


        mvc.perform(post("/api/v1/packing")
                        .with(httpBasic("admin", "secret"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(badBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(hasEntry(
                        "pedidos[0].produtos[0].dimensoes.altura",
                        "deve ser maior que 0")))
                .andExpect(jsonPath("$").value(hasEntry(
                        "pedidos[0].produtos[0].dimensoes.largura",
                        "deve ser maior que 0")))
                .andExpect(jsonPath("$").value(hasEntry(
                        "pedidos[0].produtos[0].dimensoes.comprimento",
                        "deve ser maior que 0")));

    }

    @Test
    void shouldReturn400WhenFieldsAreInvalidItem1AndItem2() throws Exception {
        var badBody = Map.of("pedidos", List.of(Map.of("pedido_id", 1, "produtos",
                List.of(
                        Map.of("produto_id", "", "dimensoes",
                                Map.of("altura", 0, "largura", 0, "comprimento", 0)
                        ),
                        Map.of("produto_id", "", "dimensoes",
                                Map.of("altura", 0, "largura", 0, "comprimento", 0)
                        )
                ))));


        mvc.perform(post("/api/v1/packing")
                        .with(httpBasic("admin", "secret"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(badBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(hasEntry(
                        "pedidos[0].produtos[0].dimensoes.altura",
                        "deve ser maior que 0")))
                .andExpect(jsonPath("$").value(hasEntry(
                        "pedidos[0].produtos[0].dimensoes.largura",
                        "deve ser maior que 0")))
                .andExpect(jsonPath("$").value(hasEntry(
                        "pedidos[0].produtos[0].dimensoes.comprimento",
                        "deve ser maior que 0")))
                .andExpect(jsonPath("$").value(hasEntry(
                        "pedidos[0].produtos[1].dimensoes.altura",
                        "deve ser maior que 0")))
                .andExpect(jsonPath("$").value(hasEntry(
                        "pedidos[0].produtos[1].dimensoes.largura",
                        "deve ser maior que 0")))
                .andExpect(jsonPath("$").value(hasEntry(
                        "pedidos[0].produtos[1].dimensoes.comprimento",
                        "deve ser maior que 0")));

    }
}
