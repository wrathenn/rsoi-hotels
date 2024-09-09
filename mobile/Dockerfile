FROM gradle:7.6.0-jdk17-jammy AS mobile-build

ENV ANDROID_HOME /opt/android-sdk

# Установка Android SDK
RUN mkdir -p ${ANDROID_HOME} && \
    sdkmanager --update && \
    yes | sdkmanager --licenses && \
    sdkmanager "platform-tools" "platforms;android-30" "build-tools;30.0.3"

# Копирование кода приложения в контейнер
COPY . /mobile

# Переход в папку проекта и сборка
WORKDIR /mobile
RUN gradle build

# Копируем скомпилированные APK в определенную директорию
RUN mkdir -p /output && cp /mobile/app/build/outputs/apk/*/*.apk /output/