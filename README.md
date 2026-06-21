# ♟️ IntelliChess

> **"Deep Blue was not intelligent at all. It was as intelligent as your alarm clock, a very expensive one, a $10 million piece. But what these machines are good for is helping chess players recognize new play patterns and mathematics."**
> — Garry Kasparov (2024)

A **bitboard-based chess engine** built in Java from scratch, with a Swing GUI and basic AI move selection.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-blue?style=for-the-badge)

---

## 🎯 Project Scope

This started as an **MCA Minor Project** focused on bitboard move generation, and grew into a full playable engine with a GUI.

**Core focus:**
1. Bitboard move generation for all 6 piece types
2. High-performance move generation via lookup tables and ray-based sliding attacks
3. A playable engine with board rendering and basic AI move selection

---

## ✅ Implemented Features

| Component | Method | Status |
|---|---|---|
| **Knight moves** | Precomputed lookup table | ✅ Complete |
| **King moves** | Precomputed lookup table | ✅ Complete |
| **Pawn moves** | Bitwise shift operations (single/double push) | ✅ Complete |
| **Rook / Bishop / Queen** | Ray-based sliding attacks with shadow casting | ✅ Complete |
| **FEN parsing** | `FenUtility` — load any board position | ✅ Complete |
| **Check detection** | Built into move generation/validation | ✅ Complete |
| **Move generation correctness** | Verified via sanity-check test suite, all pieces | ✅ Complete |
| **GUI** | Java Swing, board rendering + eval bar | ✅ Complete |
| **AI move selection** | Depth-1 (greedy) evaluation function | ✅ Complete |
| **Minimax + Alpha-Beta search** | Multi-ply lookahead for real search-based play | 🚧 Planned |

---

## 🧠 Technical Approach

### Bitboard Representation
- Each piece type stored as a 64-bit `long`
- Each bit represents a square on the 8×8 board
- Enables fast, parallel bitwise operations across all pieces of a type simultaneously

### Move Generation
- **Lookup Tables**: O(1) move generation for Knights and Kings
- **Ray Tables + Shadow Casting**: Precomputed directional rays with blocker detection for sliding pieces (Rook, Bishop, Queen)
- **Bitwise Operations**: Shift, AND, OR, XOR for move filtering and board updates

### AI / Evaluation
- Current move selection uses a **depth-1 evaluation function** (scores the resulting position directly, no opponent lookahead) — effectively a greedy heuristic-based move picker, not a full search tree yet.
- Minimax with alpha-beta pruning is the planned next step to enable genuine multi-ply lookahead.

---

## 🚀 Running It

```bash
# Run the pre-built executable JAR
java -jar intelliCHESS.jar
```

Or build from source:

```bash
mvn clean package
java -jar target/intelliCHESS-java-<version>.jar
```

---

## 📁 Project Structure

```
intelliCHESS-java/
├── src/main/java/com/sharma/intellichess/
│   ├── bitboard/
│   │   ├── BitboardUtils.java     # Square conversion, visualization
│   │   ├── Masks.java             # File/rank masks for edge detection
│   │   └── FenUtility.java        # FEN parsing → bitboard state
│   ├── movegen/
│   │   ├── KnightMoves.java       # Precomputed knight attacks
│   │   ├── KingMoves.java         # Precomputed king attacks
│   │   ├── PawnMoves.java         # Single/double push logic
│   │   └── SliderAttacks.java     # Ray-based sliding attacks (Rook/Bishop/Queen)
│   ├── engine/
│   │   └── MoveGenerator.java     # Core move generation + validation
│   ├── ai/
│   │   └── Evaluator.java         # Depth-1 evaluation function
│   ├── gui/
│   │   └── ChessGUI.java          # Swing-based board UI + eval bar
│   └── demo/
│       └── Demo.java              # Test suite
```

*(Adjust file/class names above to match your actual source tree before committing — written from commit history, not a live directory listing.)*

---

## 📊 Roadmap

- [ ] Implement minimax with alpha-beta pruning for real search depth
- [ ] Add iterative deepening / time-based search cutoff
- [ ] Benchmark move generation performance (target: 10M+ iterations)
- [ ] Improve evaluation function (piece-square tables, mobility, king safety)
- [ ] Write up technical documentation / research notes

---

## 🛠️ Tech Stack

- **Language:** Java 21 (OpenJDK)
- **Build:** Maven
- **Platform tested:** macOS (M4 Mac Mini)
- **Core engine:** Pure bitboard operations, no external libraries
- **GUI:** Java Swing

---

## 📄 License

MIT License — see [LICENSE](LICENSE)

## 👨‍💻 Author

**Sanskar Sharma**
MCA Scholar | Research Intern, IIIT Naya Raipur
GitHub: [@Sharma-Sanskar](https://github.com/Sharma-Sanskar)

Built with ☕ and bitwise operations.
