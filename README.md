# ♟️ IntelliChess

> **"Chess is the drosophila of artificial intelligence."** — Alexander Kronrod

**IntelliChess** is a modular, high-performance Chess Engine built from scratch in Java. Designed with a focus on clean architecture, bit-level optimization, and scalable AI search algorithms.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Build Status](https://img.shields.io/badge/build-passing-brightgreen?style=for-the-badge)
![License](https://img.shields.io/badge/license-MIT-blue?style=for-the-badge)

---

## 🚀 Vision

Most chess engines are black boxes of spaghetti code. **IntelliChess** aims to be different: a readable, educational, yet powerful engine that bridges the gap between basic move validation and advanced Alpha-Beta search implementation. 

Currently implementing the foundational Board Representation and FEN Parsing logic.

## ⚡ Key Features

- **Board Representation:** 8x8 char-based board (planned migration to Bitboards).
- **FEN Parsing:** Full support for Forsyth–Edwards Notation (FEN) to load any game state instantly.
- **State Management:** Tracks active color, castling rights, en passant targets, and move clocks.
- **Clean Architecture:** Strict separation between `Engine` logic, `Pieces`, and `Board` state.
- **Unit Tested:** Rigorous testing environment ensuring move reliability.

## 📌 Current Status

- IntelliChess is currently in **Phase 1: Foundation**.  
- Core board representation and FEN parsing are implemented and stable.  
- Move generation and rule enforcement are under active development.

## 🛠️ Tech Stack

- **Language:** Java 17+ (OpenJDK)
- **Build System:** Maven Standard Directory Layout
- **Testing:** JUnit 5
- **IDE:** IntelliJ IDEA

## 📦 Getting Started

### Prerequisites
- Java Development Kit (JDK) 17 or higher.
- Git.

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Sharma-Sanskar/intelliCHESS-java.git
   ```
2. **Navigate to the project**
   ```bash
   cd intelliCHESS-java
   ```
3. **Run the Engine (Console Mode)**  
   Open the project in **IntelliJ IDEA**, navigate to:  
   `src/main/java/com/sharma/intellichess/engine/Main.java`  
   and run the `Main` class.

## Roadmap
- [x] **Phase 1: Foundation**
  - Board Representation (char[][])
  - FEN String Parser
  - Gamestate Metadata (Castling, Turn, Clocks)
- [ ] **Phase 2: The Rules**
  - Move Generation (Pseudo-legal moves)
  - Check/Checkmate detection
  - Pin/Skewer logic
- [ ] **Phase 3: The Brain**
  - Evaluation Function (Material + Positional scoring)
  - Minimax Algorithm with Alpha-Beta Pruning
  - Quiescence Search
- [ ] **Phase 4: The Speed**
  - Transposition Tables
  - Iterative Deepening
  - Bitboard Migration (0x88 or Magic Bitboards)
     
## 🤝 Contributing
Contributions are welcome! Please fork the repository and submit a Pull Request.
1. Fork the Project
2. Create your Feature Branch (git checkout -b feature/AmazingFeature)
3. Commit your Changes (git commit -m 'Add some AmazingFeature')
4. Push to the Branch (git push origin feature/AmazingFeature)
5. Open a Pull Request

## 📄 License
Distributed under the MIT License. See `LICENSE` for more information.

Built with ☕ and Logic by [Sanskar Sharma](https://github.com/Sharma-Sanskar)
