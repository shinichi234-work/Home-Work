let sound; // Переменная где будет находится аудио-дорожка
let isInitialised; // Состояние, которое обозначает инициализированы ли значения или нет
let isLoaded = false;
let amplitude;
let amplitudes = [];
let fft; // Библиотека для визуализации музыки

function preload() {
    soundFormats('mp3', 'wav'); // Определяем аудио форматы, поддерживаемые плеером
    sound = loadSound('/assets/ANNA_ASTI_-_Po_baram_74376135.mp3', () => {
        console.log("sound is loaded!"); // Загружаем музыку и при успешной загрузке выводим в консоль сообщение
        isLoaded = true;
    });
    isInitialised = false;
    sound.setVolume(0.2); // Устанавливаем громкость на 20%
}

function setup() {
    createCanvas(1024, 1024);
    textAlign(CENTER); // Центрируем текст по центру
    textSize(32);

    amplitude = new p5.Amplitude();

    for (let i = 0; i < 512; i++)
        amplitudes.push(0);

    fft = new p5.FFT();
}

function draw() {
    background(0);
    fill(255);

    if (isInitialised && !sound.isPlaying()) {
        text("Press any key to play sound", width / 2, height / 2);
    } else if (sound.isPlaying()) {
        let freqs = fft.analyze();
        // Визуализатор на основе частот
        let barWidth = width / (freqs.length / 5);
        stroke(0, 150, 0);
        for (let i = 2; i < freqs.length - 2; i += 5) {
            let avg = (freqs[i - 2] + freqs[i - 1] + freqs[i] + freqs[i + 1] + freqs[i + 2]) / 5;
            let barHeight = map(avg, 0, 255, 0, height / 2);
            fill(map(i, 0, freqs.length, 0, 255), 100, 150);
            rect((i / 5) * barWidth, height - barHeight, barWidth - 2, barHeight);
        }
        noStroke();
        // Отображение волны звука
        drawWave();
    }
}

function drawWave() {
  let level = amplitude.getLevel();
        amplitudes.push(level);
        amplitudes.shift();

        let waveColor = map(level, 0, 0.2, 180, 360);
        stroke(waveColor, 100, 100);
        strokeWeight(2);
        noFill();

        beginShape();
        for (let i = 0; i < amplitudes.length; i++) {
            let y = map(amplitudes[i], 0, 0.2, height / 2, 0);
            vertex(map(i, 0, amplitudes.length, 0, width), y);
        }
        endShape();
}

function keyPressed() {
    if (!isInitialised) {
        isInitialised = true;

        let r = 1; // Скорость воспроизведения звука
        if (isLoaded)
            sound.loop(0, r); // loop - функция для зацикливания звука
    } else {
        if (key == ' ') {
            if (sound.isPaused()) sound.play();
            else sound.pause();
        }
    }
}