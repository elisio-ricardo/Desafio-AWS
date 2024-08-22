package com.elisio.desafioItau.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "veiculo")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String titulo;

    @NotEmpty
    private String autor;

    @NotNull
    private Integer qtdPaginas;

    @NotNull
    private Double preco;

    @Override
    public String toString(){
        JSONObject json = new JSONObject();
        json.put("titulo", this.titulo);
        json.put("autor", this.autor);
        json.put("qtdPaginas", this.qtdPaginas);
        json.put("preco", this.preco);

        return json.toString();
    }
}
