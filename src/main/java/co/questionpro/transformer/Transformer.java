package co.questionpro.transformer;

public interface Transformer<T, R> {
  R toEntity(T t);

  T toDTO(R r);
}
