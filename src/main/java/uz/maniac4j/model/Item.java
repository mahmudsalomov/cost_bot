package uz.maniac4j.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    private String name;

    private String amount;

    private String description;

    @Enumerated(EnumType.STRING)
    private Section section;

    @Enumerated(EnumType.STRING)
    private SectionType sectionType;

    @ManyToOne
    private Request request;

}
